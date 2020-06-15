package com.junsu.base

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

const val defaultInteger = 0
const val updateInteger = 100

const val defaultFloat = 0f
const val updateFloat = 1.1f

const val defaultLong = 0L
const val updateLong = 999999L

const val defaultBoolean = false
const val updateBoolean = true

const val defaultString = ""
const val updateString = "test"

val defaultSet = setOf<String>()
val updateSet = setOf("test")

@RunWith(AndroidJUnit4::class)
class SharedPreferenceUtilTest {

    @Test
    fun useValue() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        //Integer
        assertEquals(defaultInteger, SharedTestData.value_integer)
        SharedTestData.value_integer = updateInteger
        assertEquals(updateInteger, SharedTestData.value_integer)

        //Float
        assertEquals(defaultFloat, SharedTestData.value_float)
        SharedTestData.value_float = updateFloat
        assertEquals(updateFloat, SharedTestData.value_float)

        //Long
        assertEquals(defaultLong, SharedTestData.value_long)
        SharedTestData.value_long = updateLong
        assertEquals(updateLong, SharedTestData.value_long)

        //Boolean
        assertEquals(defaultBoolean, SharedTestData.value_boolean)
        SharedTestData.value_boolean = updateBoolean
        assertEquals(updateBoolean, SharedTestData.value_boolean)

        //String
        assertEquals(defaultString, SharedTestData.value_string)
        SharedTestData.value_string = updateString
        assertEquals(updateString, SharedTestData.value_string)

        //String Set
        assertEquals(defaultSet, SharedTestData.value_set)
        SharedTestData.value_set = updateSet
        assertEquals(updateSet, SharedTestData.value_set)
    }

    object SharedTestData: SharedPreferenceUtil("test") {
        var value_integer by intPref("value_integer", defaultInteger)
        var value_float by floatPref("value_float", defaultFloat)
        var value_long by longPref("value_long", defaultLong)
        var value_boolean by booleanPref("value_boolean", defaultBoolean)
        var value_string by stringPref("value_string", defaultString)
        var value_set by stringSetPref("value_set", defaultSet)
    }
}
