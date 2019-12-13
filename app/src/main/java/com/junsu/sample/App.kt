package com.junsu.sample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.facebook.stetho.Stetho
import com.junsu.sample.di.appModule


class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        Stetho.initializeWithDefaults(this)
        SharedData.init(applicationContext)

        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}