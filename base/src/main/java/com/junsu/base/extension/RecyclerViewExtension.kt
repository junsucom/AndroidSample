package com.junsu.base.extension

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    ContextCompat.getDrawable(
        this.context,
        drawableRes
    )?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}