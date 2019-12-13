package com.junsu.base.extension

import android.content.res.Resources

val Number.dp: Int
    get() = (this.toInt() / Resources.getSystem().displayMetrics.density).toInt()
val Number.px: Int
    get() = (this.toInt() * Resources.getSystem().displayMetrics.density).toInt()