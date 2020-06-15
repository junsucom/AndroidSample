package com.junsu.base.extension

import android.content.Context
import android.os.SystemClock
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.widget.TextView

private const val DEFAULT_DELAY_TIME = 2000L
fun View.setOnSafeClickListener(listener: View.OnClickListener) {
    setOnSafeClickListener(DEFAULT_DELAY_TIME, listener)
}
fun View.setOnSafeClickListener(delayTime: Long = DEFAULT_DELAY_TIME, listener: View.OnClickListener) {
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
fun View.setOnSafeClickListener(delayTime: Long = DEFAULT_DELAY_TIME, action: () -> Unit) {
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

fun TextView.setOnSafeEditorActionListener(delayTime: Long = DEFAULT_DELAY_TIME, action: (v:TextView, actionId: Int, event: KeyEvent?) -> Boolean) {
    this.setOnEditorActionListener { v, actionId, event ->
        when {
            SystemClock.elapsedRealtime() - lastClickTime < delayTime -> {
                lastClickTime = SystemClock.elapsedRealtime()
                true
            }
            else -> {
                lastClickTime = SystemClock.elapsedRealtime()
                action(v, actionId, event)
            }
        }
    }
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


