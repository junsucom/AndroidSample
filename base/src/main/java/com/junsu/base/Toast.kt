package com.junsu.base

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

object Toast {
    const val LENGTH_SHORT = Toast.LENGTH_SHORT
    const val LENGTH_LONG = Toast.LENGTH_LONG

    private var toast : Toast? = null

    @SuppressLint("ShowToast")
    private fun makeText(context: Context, msg: String, duration: Int) {
        toast = Toast.makeText(context, msg, duration)
    }

    fun show(context: Context?, msg: String, duration: Int = LENGTH_SHORT) {
        context?.let {
            toast?.setText(msg) ?: makeText(
                it,
                msg,
                duration
            )
            toast?.show()
        }
    }

    fun show(context: Context?, resId: Int, duration: Int = LENGTH_SHORT) {
        context?.let {
            show(it, it.getString(resId), duration)
        }
    }
}