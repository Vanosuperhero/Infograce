package com.example.infograce

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.MainActivityBinding
import com.example.infograce.recyclerview.RecyclerAdapter
import it.beppi.tristatetogglebutton_library.TriStateToggleButton.ToggleStatus


class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener , SearchView.OnQueryTextListener{

    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: RecyclerAdapter
    var indirectSwitched: Boolean = false
    var searchState: Boolean = false
    var dragState: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        val isSwitchedAny: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switch }
        val isSwitchedAll: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switch }
        binding.switchBottom.isMidSelectable = isSwitchedAny && !isSwitchedAll

        addDataSet("")

        binding.imageAdd.setOnClickListener{
            adapter.addLayer()
        }
        binding.imageDelete.setOnClickListener {
            adapter.removeLayer()
            val rotate = RotateAnimation(
                0F,
                180F,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotate.duration = 1000
            rotate.interpolator = LinearInterpolator()
            binding.imageDrag.startAnimation(rotate)
        }

        binding.imageDrag.setOnClickListener {
            dragState =! dragState
            binding.imageDrag.isSelected = dragState
            adapter.items.filterIsInstance<RecyclerViewItems.Layers>().forEachIndexed { index, value ->
                value.draggable =! value.draggable
                adapter.notifyDataSetChanged()
                adapter.touchHelper.attachToRecyclerView(binding.recyclerView)
            }
            binding.switchBottom.visibility = if(adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.draggable }) View.GONE else View.VISIBLE
        }

        binding.switchBottom.setOnToggleChanged { toggleStatus, _, _ ->
            if (!indirectSwitched) {
                when (binding.switchBottom.toggleStatus) {
                    ToggleStatus.off -> adapter.switchedOffAll()
                    ToggleStatus.mid -> adapter.switchedMidAll()
                    ToggleStatus.on -> adapter.switchedOnAll()
                    null -> {}
                }
                adapter.notifyDataSetChanged()
            }
        }

        binding.imageSearch.setOnClickListener {
            searchState =! searchState
            binding.imageSearch.isSelected = searchState
            binding.expandableSearch.visibility = if(binding.expandableSearch.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
//                highlight()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })



    }

    private fun addDataSet(search: String){
        val data = DataSource.createDataSet(search)
        adapter.submitList(data)
    }

    override fun onSwitched() {
        indirectSwitched = true
        val isSwitchedAll: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switch }
        val isSwitchedAny: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switch }
        binding.switchBottom.isMidSelectable = isSwitchedAny && !isSwitchedAll
        if (isSwitchedAll) {
            binding.switchBottom.toggleOn()
        }
        if (isSwitchedAny && !isSwitchedAll) {
            binding.switchBottom.toggleMid()
        }
        if (!isSwitchedAny) {
            binding.switchBottom.toggleOff()
        }
        indirectSwitched = false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

}
