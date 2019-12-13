package com.junsu.base

import android.content.Context
import kotlin.reflect.KProperty

abstract class SharedPreferenceUtil(val name: String) {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    private val prefs by lazy { context.getSharedPreferences(name, Context.MODE_PRIVATE) }

    interface SharedPrefsListener {
        fun onSharedPrefChanged(property: KProperty<*>)
    }
    private val listeners = mutableListOf<SharedPrefsListener>()

    fun addListener(sharedPrefsListener: SharedPrefsListener) {
        listeners.add(sharedPrefsListener)
    }

    fun removeListener(sharedPrefsListener: SharedPrefsListener) {
        listeners.remove(sharedPrefsListener)
    }

    fun clearListener() { listeners.clear() }

    private fun onPrefChanged(property: KProperty<*>) {
        listeners.forEach { it.onSharedPrefChanged(property) }
    }

    inner class StringPrefDelegate(private val prefKey: String?, private val defaultValue: String?) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) =
            prefs.getString(prefKey ?: property.name, defaultValue)

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            prefs.edit().putString(prefKey ?: property.name, value).apply()
            onPrefChanged(property)
        }
    }
    fun stringPref(prefKey: String, defaultValue: String? = null) = StringPrefDelegate(prefKey, defaultValue)

    inner class StringSetPrefDelegate(private val prefKey: String?, private val defaultValue: Set<String>?) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) : Set<String>? =
            prefs.getStringSet(prefKey ?: property.name, defaultValue)

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Set<String>?) {
            prefs.edit().putStringSet(prefKey ?: property.name, value).apply()
            onPrefChanged(property)
        }
    }
    fun stringSetPref(prefKey: String, defaultValue: Set<String>?) = StringSetPrefDelegate(prefKey, defaultValue)

    abstract class PrefDelegate<T>(val prefKey: String?) {
        abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T?
        abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    }

    inner class FloatPrefDelegate(prefKey: String?, private val defaultValue: Float) : PrefDelegate<Float>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>) =
            prefs.getFloat(prefKey ?: property.name, defaultValue)

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
            prefs.edit().putFloat(prefKey ?: property.name, value).apply()
            onPrefChanged(property)
        }
    }
    fun floatPref(prefKey: String, defaultValue: Float) = FloatPrefDelegate(prefKey, defaultValue)

    inner class LongPrefDelegate(prefKey: String?, private val defaultValue: Long) : PrefDelegate<Long>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>) =
            prefs.getLong(prefKey ?: property.name, defaultValue)

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
            prefs.edit().putLong(prefKey ?: property.name, value).apply()
            onPrefChanged(property)
        }
    }
    fun longPref(prefKey: String, defaultValue: Long) = LongPrefDelegate(prefKey, defaultValue)

    inner class IntPrefDelegate(prefKey: String?, private val defaultValue: Int) : PrefDelegate<Int>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>) =
            prefs.getInt(prefKey ?: property.name, defaultValue)

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            prefs.edit().putInt(prefKey ?: property.name, value).apply()
            onPrefChanged(property)
        }
    }
    fun intPref(prefKey: String, defaultValue: Int) = IntPrefDelegate(prefKey, defaultValue)

    inner class BooleanPrefDelegate(prefKey: String?, private val defaultValue: Boolean) : PrefDelegate<Boolean>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>) =
            prefs.getBoolean(prefKey ?: property.name, defaultValue)

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            prefs.edit().putBoolean(prefKey ?: property.name, value).apply()
            onPrefChanged(property)
        }
    }
    fun booleanPref(prefKey: String, defaultValue: Boolean) = BooleanPrefDelegate(prefKey, defaultValue)
}