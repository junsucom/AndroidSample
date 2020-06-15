package com.junsu.base

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog

object Progress {
    private var progress : AppCompatDialog? = null

    fun show(context: Context, @LayoutRes layoutRes: Int, isCancelable : Boolean = false, cancelListener : DialogInterface.OnCancelListener? = null) {
        dismiss()
        progress = LoadingProgress(
            context, layoutRes
        ).also {
            if (isCancelable) {
                it.setCancelable(true)
                it.setOnCancelListener(cancelListener)
            } else {
                it.setCancelable(false)
                try {
                    it.setOnCancelListener(null)
                } catch (e: Exception) {
                }
            }
            it.show()
        }
    }

    fun dismiss() {
        progress?.also {
            try {
                if (it.isShowing) {
                    it.setCancelable(false)
                    it.setOnCancelListener(null)
                    it.dismiss()
                }
            } catch (e: Exception) {
            }
        }
    }

    class LoadingProgress(context: Context, @LayoutRes private val layoutRes: Int) : AppCompatDialog(context) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(layoutRes)
        }
    }
}