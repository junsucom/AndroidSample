package com.junsu.base.extension

import android.content.Context
import android.os.SystemClock
import android.provider.Settings
import android.view.View


fun View.setOnSafeClickListener(delayTime: Long = 2000L, listener: View.OnClickListener) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < delayTime) {
                return
            } else {
                listener.onClick(v)
            }

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
private var lastClickTime: Long = 0
fun View.setOnSafeClickListener(delayTime: Long = 2000L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {


        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < delayTime) {
                return
            } else {
                action()
            }

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun Context.isTestLab(): Boolean {
    return "true" == Settings.System.getString(contentResolver, "firebase.test.lab")
}

fun CharSequence?.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun notNull(vararg args: Any?, action: () -> Unit) {
    when {
        args.filterNotNull().size == args.size -> action()
    }
}


