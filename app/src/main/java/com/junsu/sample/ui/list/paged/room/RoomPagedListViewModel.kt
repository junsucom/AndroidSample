package com.junsu.sample.ui.list.paged.room

import androidx.lifecycle.*
import androidx.paging.*
import com.junsu.sample.Define.PAGE_SIZE
import com.junsu.sample.model.Item
import com.junsu.sample.model.ItemDao
import com.junsu.sample.ui.list.paged.network.NetworkItemDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RoomPagedListViewModel @Inject constructor(private val itemDao: ItemDao) :
    ViewModel() {
    private val message = MutableLiveData<String>("")
    private val error = MutableLiveData<String>("")

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        itemDao.getByDate()
    }.flow.cachedIn(viewModelScope)

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
