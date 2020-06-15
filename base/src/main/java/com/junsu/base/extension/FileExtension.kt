package com.junsu.base.extension

import android.webkit.MimeTypeMap
import java.io.File
import java.util.*

fun File.getMimeType(fallback: String = "*/*"): String {
    return MimeTypeMap.getFileExtensionFromUrl(toString())
        ?.run { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.ROOT)) }
        ?: fallback
}