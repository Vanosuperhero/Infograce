package com.example.infograce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.RecyclerViewItems

class MainViewModel : ViewModel() {
    private var _itemsList = mutableListOf<RecyclerViewItems>()
    val itemsList: List<RecyclerViewItems>
        get() = _itemsList

    private val _itemsListLiveData = MutableLiveData<MutableList<RecyclerViewItems>>()
    val itemsListLiveData: LiveData<MutableList<RecyclerViewItems>>
        get() = _itemsListLiveData

    init {
        _itemsList = DataSource.createDataSet()
        _itemsListLiveData.value = _itemsList
    }

}