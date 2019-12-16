package com.junsu.base.extension

import android.graphics.*
import java.io.ByteArrayOutputStream
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

fun Bitmap.resizeBitmap(maxImageSize: Float, filter: Boolean = false): Bitmap {
    val ratio = min(maxImageSize / this.width, maxImageSize / this.height)
    val width = (ratio * this.width).roundToInt()
    val height = (ratio * this.height).roundToInt()

    return Bitmap.createScaledBitmap(this, width, height, filter)
}

fun Bitmap.toByteArray(
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100
): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(format, quality, stream)
    return stream.toByteArray()
}

fun Bitmap.fillColor(
    color1: Int = Color.argb(255, Random.nextInt(0,255), Random.nextInt(0,255), Random.nextInt(0,255)),
    color2: Int? = Color.argb(255, Random.nextInt(0,255), Random.nextInt(0,255), Random.nextInt(0,255)),
    x0: Float = 0f,
    y0: Float = 0f,
    x1: Float = 0f,
    y1: Float = height.toFloat()
): Bitmap {
    val canvas = Canvas(this)
    val paint = Paint()
    if (color2 == null) {
        paint.color = color1
    } else {
        val shader =
            LinearGradient(x0, y0, x1, y1, color1, color2, Shader.TileMode.CLAMP)
        paint.shader = shader
    }

    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    return this
}
