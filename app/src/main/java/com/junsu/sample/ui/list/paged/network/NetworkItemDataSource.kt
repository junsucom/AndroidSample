package com.junsu.sample.ui.list.paged.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.junsu.sample.Define.PAGE_SIZE
import com.junsu.sample.model.Item
import com.junsu.sample.ui.list.paged.network.api.ItemService

class NetworkItemDataSource (
    private val itemService: ItemService
): PagingSource<Long, Item>() {

    override suspend fun load(
        params: LoadParams<Long>
    ): LoadResult<Long, Item> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response:List<Item> = itemService.getItems((nextPageNumber - 1) * PAGE_SIZE, PAGE_SIZE).items?: listOf()
            LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, Item>): Long? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}