package com.junsu.sample.sector.list.paged.network

import androidx.paging.PageKeyedDataSource
import com.junsu.sample.Define.PAGE_SIZE
import com.junsu.sample.model.Item
import com.junsu.sample.sector.list.paged.network.api.ItemService
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class NetworkItemDataSource (
    private val itemService: ItemService,
    private val scope: CoroutineScope
): PageKeyedDataSource<Long, Item>() {
    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Item>) {

    }

    // FOR DATA ---
    private var supervisorJob = SupervisorJob()
    //...

    // OVERRIDE ---
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Item>) {
        // ...
        executeQuery(Date().time) {
            callback.onResult(it, null, it.last().id)
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Item>) {
        val page = params.key
        // ...
        executeQuery(page) {
            try {
                callback.onResult(it, it.last().id)
            } catch (e: NoSuchElementException) {
                callback.onResult(it, null)
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)

    }

    // UTILS ---
    private fun executeQuery(startIndex: Long, callback:(List<Item>) -> Unit) {
        // ...
        scope.launch(supervisorJob + errorHandler) {
            delay(200) // To handle user typing case
            itemService.getItems(startIndex, PAGE_SIZE).items?.also(callback)
        }
    }
}