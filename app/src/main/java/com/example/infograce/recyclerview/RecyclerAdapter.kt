package com.example.infograce.recyclerview


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.text.clearSpans
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.MainFragment
import com.example.infograce.R
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.LayersGroupBinding
import com.example.infograce.dataClass.models.Layer
import java.util.*


class RecyclerAdapter(
    private val listenerActivity: MainFragment,
    private val gestureCallbacks: GestureCallbacks
) : RecyclerView.Adapter<RecyclerViewHolders>(), Filterable {

    var items: MutableList<RecyclerViewItems> = ArrayList()
    var filteredItems: MutableList<RecyclerViewItems> = ArrayList()
    var isDraggable: Boolean = false
    private var isVisible: Boolean = false
    var queryText = ""

    var itemChangeListener: ((String, Float) -> Unit)? = null
    var itemCreateListener: ((RecyclerViewItems.Layers) -> Unit)? = null
    var itemVisibilityListener: ((Layer, Boolean) -> Unit)? = null
    var itemRemoveListener: ((String) -> Unit)? = null
    var itemMoveListener: ((String, String) -> Unit)? = null

    private val spanHighlight by lazy {
        BackgroundColorSpan(
            Color.parseColor("#59BD87")
        )
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolders, position: Int) {
        when (holder) {
            is RecyclerViewHolders.LayersGroupViewHolder -> {
                holder.bind(
                    filteredItems[position] as RecyclerViewItems.LayersGroup
                )
                if (filteredItems[position] == filteredItems[0])
                    holder.binding.textViewTitle.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        setMargins(0, 50, 0, 0)
                    }

            }
            is RecyclerViewHolders.LayersViewHolder -> {
                val currentItem = filteredItems[position]
                if (currentItem is RecyclerViewItems.Layers) {
                    itemCreateListener?.invoke(currentItem)

                    holder.bind(currentItem)
                    if (queryText.isNotEmpty()) {
                        val startPos =
                            currentItem.title.title.toString().lowercase()
                                .indexOf(queryText.lowercase())
                        val endPos = startPos + queryText.length
                        if (startPos != -1) {
                            if (queryText.let { currentItem.title.title.contains(it, true) }) {
                                currentItem.title.title.setSpan(
                                    spanHighlight,
                                    startPos,
                                    endPos,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                        }
                    } else {
                        currentItem.title.title.clearSpans()
                    }
                    holder.bind(currentItem)

                    isVisible = currentItem.visibility
                    holder.binding.expandable.visibility =
                        if (isVisible) View.VISIBLE else View.GONE

                    if (isVisible) {
                        holder.binding.titleView.setTypeface(null, Typeface.BOLD)
                        holder.binding.titleView.setTextColor(Color.parseColor("#59BD87"))
                        holder.binding.iconView.setColorFilter(Color.parseColor("#59BD87"))
                        holder.binding.chevron.setImageResource(R.drawable.chevron_reverse)
                    } else {
                        holder.binding.titleView.setTypeface(null, Typeface.NORMAL)
                        holder.binding.titleView.setTextColor(Color.WHITE)
                        holder.binding.iconView.setColorFilter(Color.WHITE)
                        holder.binding.chevron.setImageResource(R.drawable.chevron_right)
                    }


                    val isEnable: Boolean = currentItem.enable
                    holder.binding.titleLine.alpha = if (isEnable) 1f else 0.5f
                    holder.binding.invisibleView.visibility =
                        if (isEnable) View.GONE else View.VISIBLE

                    holder.binding.switch2.visibility =
                        if (isDraggable) View.INVISIBLE else View.VISIBLE
                    holder.binding.dragView.visibility =
                        if (isDraggable) View.VISIBLE else View.GONE

                    holder.binding.switch2.setOnClickListener {
                        listenerActivity.onSwitched()
                        currentItem.switchSave = holder.binding.switch2.isChecked

                    }
                    holder.binding.switch2.setOnCheckedChangeListener { _, isChecked ->
                        currentItem.switch = isChecked
                        itemVisibilityListener?.invoke(currentItem.data, isChecked)
                    }

                    holder.binding.dragView.setOnTouchListener { _, event ->
                        if (event.action == MotionEvent.ACTION_DOWN) {
                            gestureCallbacks.onStartDrag(holder)
                        }
                        false
                    }

                    holder.binding.switch2.isChecked = currentItem.switch


                    holder.binding.titleLine.setOnClickListener {
                        if (!isDraggable) {
                            if (currentItem.visibility) {
                                currentItem.visibility = false
                            } else {
                                if (currentItem == filteredItems.last()) {
                                    listenerActivity.onScroll()
                                }
                                closeLayers()
                                currentItem.visibility = true
                            }
                            notifyItemChanged(position)
                        }
                    }

                    holder.binding.titleLine.setOnLongClickListener {
                        currentItem.enable = !currentItem.enable
                        notifyItemChanged(position)
                        true
                    }

                    holder.binding.slider.addOnChangeListener { _, value, _ ->
                        holder.binding.transViewNum.text = "${value.toInt()}%"
                        itemChangeListener?.invoke(currentItem.data.layerId, value)
                    }
                    if (filteredItems[position] == filteredItems.last())
                        holder.binding.expandable.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            setMargins(0, 0, 0, 50)
                        }
                    else {
                        holder.binding.expandable.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            setMargins(0, 0, 0, 0)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolders {
        return when (viewType) {
            R.layout.layer_group -> RecyclerViewHolders.LayersViewHolder(
                LayerGroupBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            R.layout.layers_group -> RecyclerViewHolders.LayersGroupViewHolder(
                LayersGroupBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided ")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (filteredItems[position]) {
            is RecyclerViewItems.TitleSpannable -> R.layout.layer_group
            is RecyclerViewItems.LayersGroup -> R.layout.layers_group
            is RecyclerViewItems.Layers -> R.layout.layer_group
        }
    }

    fun submitList(list: MutableList<RecyclerViewItems>) {
        items = list
        filteredItems = list
    }

    fun switchedOffAll() {
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = false }
    }

    fun switchedOnAll() {
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = true }
    }

    fun switchedMidAll() {
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = it.switchSave }
    }

    fun resetSwitchSaveAll() {
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switchSave = false }
    }

    fun switchSaveToSwitch() {
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switchSave = it.switch }
    }

    fun closeLayers() {
        filteredItems.mapIndexed { index, t ->
            if (t is RecyclerViewItems.Layers && t.visibility) {
                t.visibility = false
                notifyItemChanged(index)
            }
        }
    }

    fun addLayer(layer: RecyclerViewItems.Layers) {
        filteredItems.add(layer)
        notifyItemChanged(itemCount)
    }

    fun removeLayer() {
        if (filteredItems.isNotEmpty()) {
            val lastItem = filteredItems.last()
            if (lastItem is RecyclerViewItems.Layers) {
                itemRemoveListener?.invoke(lastItem.data.layerId)
            }
            filteredItems.removeLast()
            notifyItemChanged(itemCount)
        }
    }

    interface Listener {
        fun onSwitched()
        fun onScroll()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                queryText = charString
                val filteredList = ArrayList<RecyclerViewItems>()
                items.filterIsInstance<RecyclerViewItems.Layers>().filter {
                    (it.title.title.toString().lowercase()
                        .contains(constraint.toString().lowercase()))
                }
                    .forEach { filteredList.add(it) }

                filteredItems = items.filterIndexed { _, recyclerViewItems ->
                    recyclerViewItems in filteredList || recyclerViewItems is RecyclerViewItems.LayersGroup
                }.toMutableList()
                if (filteredItems.last() is RecyclerViewItems.LayersGroup) filteredItems.removeLast()
                filteredItems = filteredItems.filter {
                    it is RecyclerViewItems.Layers || filteredItems[filteredItems.indexOf(it) + 1] !is RecyclerViewItems.LayersGroup
                }.toMutableList()
                return FilterResults().apply {
                    values = if (charString.isEmpty()) items else filteredItems
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = if (results?.values == null) {
                    ArrayList()
                } else
                    results.values as MutableList<RecyclerViewItems>
                notifyDataSetChanged()
            }
        }
    }

    fun onItemMoved(fromPosition: Int, toPosition: Int): Boolean {
        val fromItem = filteredItems[fromPosition]
        val toItem = filteredItems[toPosition]

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(filteredItems, i, i + 1)
                if ((fromItem is RecyclerViewItems.Layers) && (toItem is RecyclerViewItems.Layers))
                    itemMoveListener?.invoke(fromItem.data.layerId, toItem.data.layerId)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(filteredItems, i, i - 1)
                if ((fromItem is RecyclerViewItems.Layers) && (toItem is RecyclerViewItems.Layers))
                    itemMoveListener?.invoke(toItem.data.layerId, fromItem.data.layerId)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        notifyItemChanged(fromPosition, toPosition)
        return true
    }

}