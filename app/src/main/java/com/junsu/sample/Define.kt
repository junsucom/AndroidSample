package com.junsu.sample

import java.text.SimpleDateFormat
import java.util.*

object Define {
    const val TAG = "sample"

    const val DATABASE_NAME = "sample-db"
    const val TABLE_ITEM = "tbItem"

    const val PAGE_SIZE = 30

    val DATE_FORMAT = SimpleDateFormat("MM월dd일 aa hh:mm", Locale.KOREA)

    const val URL_HOST = "https://gorest.co.in/"
    const val URL_GET_ITEM = "dummy/items"
    const val PARAM_GET_ITEM_START_INDEX = "startIndex"
    const val PARAM_GET_ITEM_COUNT = "count"
}