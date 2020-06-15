package com.junsu.base.extension

import org.junit.Assert
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun encrypt() {
        val secretKey = "12345678901234567890123456789012"
        val orgText = "abcde"
        val encryptedText = orgText.encryptAES256(secretKey)
        println(encryptedText)
        val decryptedText = encryptedText.decryptAES256(secretKey)
        println(decryptedText)
        Assert.assertEquals(orgText, decryptedText)
    }
}