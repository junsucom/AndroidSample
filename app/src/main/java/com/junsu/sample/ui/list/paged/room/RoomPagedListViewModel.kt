package com.junsu.sample.ui.list.paged.room

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.junsu.sample.Define.PAGE_SIZE
import com.junsu.sample.model.Item
import com.junsu.sample.model.ItemDao
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class RoomPagedListViewModel @ViewModelInject constructor(private val itemDao: ItemDao) :
    ViewModel() {
    private val message = MutableLiveData<String>("")
    private val error = MutableLiveData<String>("")
    val itemList: LiveData<PagedList<Item>> by lazy {
        val dataSourceFactory = itemDao.getByDate()
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()
        LivePagedListBuilder(dataSourceFactory, config).build()
    }

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)

        if(!exception.message.isNullOrEmpty()) {
            error.postValue(exception.message)
        }
    }

    fun send(msg: String? = message.value) {
        msg?.also { m ->
            viewModelScope.launch(errorHandler) {
                Timber.d("send:${m}")
            }
        }
    }
}
