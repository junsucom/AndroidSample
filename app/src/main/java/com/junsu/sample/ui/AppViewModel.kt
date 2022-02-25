package com.junsu.sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 공통으로 사용하는 viewModel
 *
 * Fragment 등에서 사용시
 * private val commonViewModel by activityViewModels<CommonViewModel>()
 * 와 같이 activityViewModels 을 이용 하자
 */
@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    //Simple Number LiveData
    private val _numberData = MutableLiveData(0)

    val numberData: LiveData<Int> = _numberData

    fun addNumberData() {
        _numberData.postValue((_numberData.value ?: 0) + 1)
    }
}