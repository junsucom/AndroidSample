package com.junsu.sample.ui.list.paged.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.junsu.sample.ui.list.paged.network.api.ItemService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkPagedListViewModel @Inject constructor(private val itemService: ItemService) : ViewModel() {
    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        NetworkItemDataSource(itemService)
    }.flow.cachedIn(viewModelScope)
}
