package com.junsu.sample

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

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

    override fun getWorkManagerConfiguration() = Configuration.Builder().run {
        if (BuildConfig.DEBUG) {
            setMinimumLoggingLevel(android.util.Log.DEBUG)
        }
        setWorkerFactory(workerFactory)
        build()
    }
}