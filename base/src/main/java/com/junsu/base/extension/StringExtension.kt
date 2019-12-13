package com.junsu.base.extension

import android.graphics.Paint

fun String.replaceBreakWord(
    _paint: Paint,
    maxWidth: Int
): String? {
    var formattedText = ""
    var workingText = ""

    val newPart = (if (workingText.length > 0) " " else "") + this
    workingText += newPart
    val width = _paint.measureText(workingText, 0, workingText.length) as Int
    if (width > maxWidth) {
        formattedText += (if (formattedText.length > 0) "\n" else "") + workingText.substring(
            0,
            workingText.length - newPart.length
        )
        workingText = this
    }

    if (workingText.length > 0) formattedText += (if (formattedText.length > 0) "\n" else "") + workingText
    return formattedText
}