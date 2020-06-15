package com.junsu.base.extension

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.IOException

@Throws(IOException::class)
fun Uri.toCacheFile(context: Context, prefix: String = "prefix", suffix: String = "suffix"): File? {
    return context.contentResolver.openInputStream(this)?.let {inputStream ->
        File.createTempFile(prefix, suffix, context.cacheDir).also {  file ->
            inputStream.copyTo(file.outputStream())
        }
    }
}

fun Uri.getMimeType(context: Context, fallback: String = "*/*"): String {
    return context.contentResolver.getType(this)?:fallback
}