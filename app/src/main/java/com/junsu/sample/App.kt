package com.junsu.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        SharedData.init(applicationContext)
    }
}