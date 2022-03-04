package com.junsu.base.extension

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_FASTEST
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Context.sensorChangesFlow(type: Int, once: Boolean = true): Flow<SensorEvent> = callbackFlow {
    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                trySend(event)
            }
            if(once) {
                close()
            }
        }
    }

    val result = sensorManager.registerListener(
        sensorEventListener,
        sensorManager.getDefaultSensor(type),
        SENSOR_DELAY_FASTEST
    )
    if(!result) {
        error("sensor error")
    }
    awaitClose {
        sensorManager.unregisterListener(sensorEventListener)
    }
}