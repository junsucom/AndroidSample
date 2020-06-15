package com.junsu.base.extension

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int, betweenOnly:Boolean = true) {
    if(betweenOnly) {
        BetweenDividerItemDecoration().also { decoration ->
            ContextCompat.getDrawable(
                this.context,
                drawableRes
            )?.also { decoration.setDrawable(it) }
            addItemDecoration(decoration)
        }
    } else {
        DividerItemDecoration(
            this.context,
            DividerItemDecoration.VERTICAL
        ).also { decoration ->
            ContextCompat.getDrawable(
                this.context,
                drawableRes
            )?.let {
                decoration.setDrawable(it)
                addItemDecoration(decoration)
            }
        }
    }
}

private class BetweenDividerItemDecoration : RecyclerView.ItemDecoration() {
    private var mDivider: Drawable? = null

    fun setDrawable(drawable: Drawable) {
        mDivider = drawable
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        mDivider?.also {
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params =
                    child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom: Int = top + it.intrinsicHeight
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}
fun RecyclerView.setSpace(space: Int) {
    addItemDecoration(SpacesItemDecoration(space));
}

private class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}