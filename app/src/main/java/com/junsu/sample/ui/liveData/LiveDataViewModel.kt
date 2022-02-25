package com.junsu.sample.ui.liveData

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LiveDataViewModel @Inject constructor() : ViewModel() {
    //Simple Number LiveData
    private val _numberData = MutableLiveData(0)

    val numberData: LiveData<Int> = _numberData

    fun addNumberData() {
        _numberData.postValue((_numberData.value ?: 0) + 1)
    }

    // ArrayList LiveData
    private val _arrayListData = arrayListOf("aa", "bb")
    private val _listData = MutableLiveData(_arrayListData)
    val listData: LiveData<ArrayList<String>> = _listData

    fun addListData(data: String) {
        _arrayListData.add(data)
        _listData.postValue(_arrayListData)
    }

    //MediatorLiveData
    val mediatorResult = MediatorLiveData<Int>()
}
