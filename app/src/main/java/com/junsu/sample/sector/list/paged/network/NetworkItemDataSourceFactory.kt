package com.junsu.sample.sector.list.paged.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.junsu.sample.model.Item
import com.junsu.sample.sector.list.paged.network.api.ItemService
import kotlinx.coroutines.CoroutineScope

class NetworkItemDataSourceFactory(private val notificationService: ItemService,
                                   private val scope: CoroutineScope
): DataSource.Factory<Long, Item>() {

    private val source = MutableLiveData<NetworkItemDataSource>()

    override fun create(): DataSource<Long, Item> {
        val source = NetworkItemDataSource(notificationService, scope)
        this.source.postValue(source)
        return source
    }
}