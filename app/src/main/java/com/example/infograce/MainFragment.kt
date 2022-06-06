package com.example.infograce

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.dataClass.AddLayer
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.dataClass.models.MapStyle
import com.example.infograce.databinding.FragmentMainBinding
import com.example.infograce.helper.LocationPermissionHelper
import com.example.infograce.recyclerview.GestureCallbacks
import com.example.infograce.recyclerview.ItemTouchHelperCallback
import com.example.infograce.recyclerview.RecyclerAdapter
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.Style
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.rm.rmswitch.RMTristateSwitch
import java.lang.ref.WeakReference


class MainFragment : Fragment(), RecyclerAdapter.Listener, SearchView.OnQueryTextListener,
    GestureCallbacks {
    private val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(this))
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val adapter by lazy { RecyclerAdapter(this, this) }
    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private val mapboxMap by lazy { mapView.getMapboxMap() }
    private val mapView by lazy { binding.mapView }
    private var indirectSwitched: Boolean = false
    private var searchState: Boolean = false
    private var dragState: Boolean = false
    private var lastInput: String = ""
    private var currentStyle = MapStyle.SATELLITE
    private val locationPermissionHelper by lazy {
        LocationPermissionHelper(WeakReference(requireActivity()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        addDataSet()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDrawerLayout()
        setupToolbarMenu()
        enableLocation()

//        val apiService = ApiFactory.apiService
//        MainScope().launch {
//            val a = apiService.getListOfTilesets().filter {
//                it.type == "vector"
//            }
//            Log.d("TAG", "getListOfTilesets() $a")
//        }

        with(adapter) {
            itemCreateListener = { layer ->
                addSourceAndLayers(layer)
            }

            itemMoveListener = { fromId, toId ->
                mapboxMap.getStyle {
                    it.moveStyleLayer(fromId, LayerPosition(null, toId, null))
                }
            }

            itemChangeListener = { layerId, value ->
                mapboxMap.getStyle {
                    val layer = it.getLayerAs<FillLayer>(layerId)
                    layer?.fillOpacity(value / 100.0)
                }
            }

            itemVisibilityListener = { tilesetLayer, isChecked ->
                mapboxMap.getStyle { style ->
                    style.getLayer(tilesetLayer.layerId)?.let { layer ->
                        if (isChecked)
                            layer.visibility(Visibility.VISIBLE)
                        else
                            layer.visibility(Visibility.NONE)

                    }
                }
            }

            itemRemoveListener = { layerId ->
                mapboxMap.getStyle { style ->
                    if (style.styleLayerExists(layerId)) style.removeStyleLayer(layerId)
                }
            }
        }

        with(binding) {
            imageAdd.setOnClickListener {
                addLayer()
            }
            imageDelete.setOnClickListener {
                adapter.removeLayer()
            }

            commonSwitchParent.setOnClickListener {
                commonSwitch.performClick()
            }

            imageDrag.setOnClickListener {
                dragState = !dragState
                imageDrag.isSelected = dragState
                adapter.isDraggable = !adapter.isDraggable
                if (dragState) {
                    itemTouchHelper.attachToRecyclerView(recyclerView)
                } else itemTouchHelper.attachToRecyclerView(null)
                commonSwitchParent.visibility = if (dragState) View.GONE else View.VISIBLE
                adapter.notifyDataSetChanged()
                adapter.closeLayers()
                val scale = resources.displayMetrics.density
                if (dragState) {
                    it.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        marginEnd = 0
                    }
                    it.setPadding(
                        (20 * scale).toInt(),
                        (14 * scale).toInt(),
                        (20 * scale).toInt(),
                        (14 * scale).toInt()
                    )
                } else {
                    it.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        marginEnd = (2 * scale).toInt()
                    }
                    it.setPadding(
                        (12 * scale).toInt(),
                        (14 * scale).toInt(),
                        (12 * scale).toInt(),
                        (14 * scale).toInt()
                    )
                }
            }

            fun switchFun(
                switchView: RMTristateSwitch,
                isSwitchedAny1: Boolean,
                isSwitchedAll1: Boolean
            ) {
                when (switchView.state) {
                    RMTristateSwitch.STATE_LEFT -> adapter.switchedOffAll()
                    RMTristateSwitch.STATE_MIDDLE -> {
                        if (isSwitchedAny1 && !isSwitchedAll1) {
                            adapter.switchedMidAll()
                        } else {
                            switchView.state = RMTristateSwitch.STATE_RIGHT
                            switchFun(switchView, isSwitchedAny1, !isSwitchedAll1)
                        }
                    }
                    RMTristateSwitch.STATE_RIGHT -> adapter.switchedOnAll()
                }
                adapter.notifyDataSetChanged()
            }

            commonSwitch.addSwitchObserver { switchView, _ ->
                val isSwitchedAny1: Boolean =
                    adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switchSave }
                val isSwitchedAll1: Boolean =
                    adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switchSave }
                if (!indirectSwitched) {
                    switchFun(switchView, isSwitchedAny1, isSwitchedAll1)
                }
            }

            fabChangeStyle.setOnClickListener {
                val itemsList = adapter.items
                when (currentStyle) {
                    MapStyle.MAPBOX_STREETS -> mapboxMap.loadStyleUri(Style.MAPBOX_STREETS,
                        {
                            currentStyle = MapStyle.SATELLITE
                            addSourcesAndLayers(itemsList)
                        },
                        object : OnMapLoadErrorListener {
                            override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
                                showErrorToast(eventData)
                            }
                        })
                    MapStyle.SATELLITE -> mapboxMap.loadStyleUri(Style.SATELLITE,
                        {
                            currentStyle = MapStyle.SATELLITE_STREETS
                            addSourcesAndLayers(itemsList)
                        },
                        object : OnMapLoadErrorListener {
                            override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
                                showErrorToast(eventData)
                            }
                        })
                    MapStyle.SATELLITE_STREETS -> mapboxMap.loadStyleUri(Style.SATELLITE_STREETS,
                        {
                            currentStyle = MapStyle.MAPBOX_STREETS
                            addSourcesAndLayers(itemsList)
                        },
                        object : OnMapLoadErrorListener {
                            override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
                                showErrorToast(eventData)
                            }
                        }
                    )
                }
            }
        }

        fun showSoftKeyboard(view: View) {
            if (view.requestFocus()) {
                val imm: InputMethodManager =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        fun hideSoftKeyboard(view: View) {
            val imm =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding.imageSearch.setOnClickListener {
            adapter.closeLayers()
            searchState = !searchState
            if (!searchState) {
                lastInput = binding.editSearch.text.toString()
                binding.editSearch.text?.clear()
                hideSoftKeyboard(binding.editSearch)
            } else {
                binding.editSearch.setText(lastInput)
                binding.editSearch.text?.let { it1 -> binding.editSearch.setSelection(it1.length) }
                showSoftKeyboard(binding.editSearch)
            }
            binding.imageSearch.isSelected = searchState
            binding.expandableSearch.visibility =
                if (binding.expandableSearch.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            binding.recyclerView.scrollToPosition(0)
        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupToolbarMenu() {
        setHasOptionsMenu(true)
        val colorFilter = PorterDuffColorFilter(
            requireContext().getColor(R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
        binding.toolbar.navigationIcon?.colorFilter = colorFilter
    }

    private fun addSourcesAndLayers(itemsList: MutableList<RecyclerViewItems>) {
        for (layer in itemsList)
            if (layer is RecyclerViewItems.Layers)
                addSourceAndLayers(layer)
    }

    private fun addSourceAndLayers(layer: RecyclerViewItems.Layers) {
        mapboxMap.getStyle {
            if (it.getSource(layer.data.source.sourceId) == null)
                it.addSource(
                    vectorSource(layer.data.source.sourceId) { url(layer.data.source.url) }
                )
            if (it.getLayer(layer.data.layerId) == null)
                it.addLayer(
                    fillLayer(layer.data.layerId, layer.data.source.sourceId) {
                        sourceLayer(layer.data.sourceLayer)
                        fillOpacity(layer.data.fillOpacity)
                        fillColor(layer.data.fillColor)
                        fillOutlineColor(layer.data.fillOutlineColor)
                        visibility(if (layer.visibility) Visibility.VISIBLE else Visibility.NONE)
                    })
        }
    }

    private fun showErrorToast(eventData: MapLoadingErrorEventData) {
        Toast.makeText(
            this@MainFragment.requireContext(),
            eventData.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addDataSet() {
        viewModel.itemsListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun addLayer() {
        val layer = AddLayer.createLayer()
        adapter.addLayer(layer)
    }


    override fun onSwitched() {
        indirectSwitched = true
        val isSwitchedAll: Boolean =
            adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switch }
        val isSwitchedAny: Boolean =
            adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switch }

        when (binding.commonSwitch.state) {
            RMTristateSwitch.STATE_LEFT -> adapter.resetSwitchSaveAll()
            RMTristateSwitch.STATE_RIGHT -> adapter.switchSaveToSwitch()
            RMTristateSwitch.STATE_MIDDLE -> {}
        }

        if (isSwitchedAll) {
            binding.commonSwitch.state = RMTristateSwitch.STATE_RIGHT
        }
        if (isSwitchedAny && !isSwitchedAll) {
            binding.commonSwitch.state = RMTristateSwitch.STATE_MIDDLE
        }
        if (!isSwitchedAny) {
            binding.commonSwitch.state = RMTristateSwitch.STATE_LEFT
        }
        indirectSwitched = false

    }

    override fun onScroll() {
        binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int): Boolean {
        return adapter.onItemMoved(fromPosition, toPosition)
    }

    private fun enableLocation() {
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.navigation,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.note,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(requireActivity(), "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures
            .removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        with(mapView) {
            location.apply {
                removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
                removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            }
            gestures.removeOnMoveListener(onMoveListener)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupDrawerLayout() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(
                requireActivity(),
                binding.drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}