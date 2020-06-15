package com.junsu.sample.ui.list.paged.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.junsu.sample.Define.PAGE_SIZE
import com.junsu.sample.model.Item
import com.junsu.sample.ui.list.paged.network.api.ItemService

class NetworkPagedListViewModel(private val itemService: ItemService) : ViewModel() {
    private val itemDataSourceFactory =
        NetworkItemDataSourceFactory(
            itemService,
            viewModelScope
        )

    val message = MutableLiveData<String>("")
    val error = MutableLiveData<String>("")
    val itemList: LiveData<PagedList<Item>> by lazy {
        val dataSourceFactory = itemDataSourceFactory
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()
        LivePagedListBuilder(dataSourceFactory, config).build()
    }
}
