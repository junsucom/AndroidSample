package com.junsu.sample.section.list.paged.network.api

import com.junsu.sample.Define
import retrofit2.http.GET
import retrofit2.http.Query


interface ItemService {
    @GET(Define.URL_GET_ITEM)
    suspend fun getItems(@Query(Define.PARAM_GET_ITEM_START_INDEX) startIndex: Long,
                                @Query(Define.PARAM_GET_ITEM_COUNT) count: Int): ItemResponse
}
