package com.junsu.sample.util

import android.graphics.Bitmap
import com.google.gson.Gson
import com.junsu.base.extension.fillColor
import com.junsu.base.extension.px
import com.junsu.base.extension.toByteArray
import com.junsu.sample.Define
import com.junsu.sample.model.Item
import com.junsu.sample.model.ItemType
import com.junsu.sample.ui.list.paged.network.api.ItemResponse
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.*


private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//    this.level = HttpLoggingInterceptor.Level.BODY
}

private class ItemDummyInterceptor : Interceptor {
    private fun makeFakeResponse(startIndex: Long, count: Int) :String {
        val fakeNotification = arrayListOf<Item>().apply {
            for (index in startIndex until startIndex+count) {
                val now = Date(Date().time  - (index * 60000))
                val item = Item(
                    id = index,
                    type = ItemType.TypeA,
                    title = "title $index",
                    message = "message $index",
                    date = Calendar.getInstance().also {
                        it.time = now
                    },
                    image = Bitmap.createBitmap(120.px, 120.px, Bitmap.Config.ARGB_8888).fillColor()
                        .toByteArray()
                )
                add(item)
            }
        }
        return Gson().toJson(
            ItemResponse(
                fakeNotification,
                startIndex
            )
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (chain.request().url.toString().contains(Define.URL_GET_ITEM)) {
            val startIndex = chain.request().url.queryParameter(Define.PARAM_GET_ITEM_START_INDEX)?.toLong()?:0
            val count = chain.request().url.queryParameter(Define.PARAM_GET_ITEM_COUNT)?.toInt()?:0
            Timber.d("intercept startIndex:$startIndex, count:$count")
            val responseString = makeFakeResponse(startIndex, count)
            Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(responseString.toResponseBody("application/json".toMediaType()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            chain.proceed(chain.request())
        }
    }
}


val apiInterceptor : OkHttpClient = OkHttpClient.Builder().apply {
    addInterceptor(interceptor)
    addInterceptor(ItemDummyInterceptor())
}.build()