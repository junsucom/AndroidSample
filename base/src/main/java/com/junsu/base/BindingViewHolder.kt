package com.junsu.base

import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewHolder<out T : ViewDataBinding, in U>(view: View) : RecyclerView.ViewHolder(view) {
    val context: Context = view.context
    val binding: T = DataBindingUtil.bind(view)!!
    abstract fun bindTo(data: U)
    abstract fun clear()
}
