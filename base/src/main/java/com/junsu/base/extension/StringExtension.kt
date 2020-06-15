package com.junsu.base.extension

import android.graphics.Paint
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun String.replaceBreakWord(
    _paint: Paint,
    maxWidth: Int
): String? {
    var formattedText = ""
    var workingText = ""

    val newPart = (if (workingText.length > 0) " " else "") + this
    workingText += newPart
    val width = _paint.measureText(workingText, 0, workingText.length).toInt()
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

private fun cipher(opmode:Int, secretKey:String): Cipher {
    if(secretKey.length != 32) throw RuntimeException("SecretKey length is not 32 chars")
    val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
    val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
    c.init(opmode, sk, iv)
    return c
}

fun String.encryptAES256(secretKey:String):String{
    val encrypted = cipher(
        Cipher.ENCRYPT_MODE,
        secretKey
    ).doFinal(this.toByteArray(Charsets.UTF_8))
    return String(Base64.encode(encrypted, Base64.DEFAULT))
}

fun String.decryptAES256(secretKey:String):String{
    val byteStr = Base64.decode(this.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
    return String(
        cipher(Cipher.DECRYPT_MODE, secretKey).doFinal(byteStr))
}