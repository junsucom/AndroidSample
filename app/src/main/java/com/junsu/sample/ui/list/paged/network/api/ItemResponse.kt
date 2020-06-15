package com.junsu.sample.ui.list.paged.network.api

import com.google.gson.annotations.SerializedName
import com.junsu.sample.model.Item

data class ItemResponse(
    @field:SerializedName("items")
    var items: List<Item>? = null,
    @field:SerializedName("startIndex")
    var startIndex: Long
)