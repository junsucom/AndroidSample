package com.junsu.sample.service

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.JobIntentService
import timber.log.Timber


class BootService : JobIntentService() {
    companion object {
        private const val ACTION_BOOT_SERVICE = "com.junsu.sample.service.actionBootService"
        fun start(context: Context) {
            Intent(context, BootService::class.java).also { intent ->
                intent.action = ACTION_BOOT_SERVICE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            }
        }
    }

    override fun onHandleWork(intent: Intent) {
        Timber.d("${intent.action}")
        when (intent?.action) {
            ACTION_BOOT_SERVICE -> {
                Thread.sleep(5000)
                Timber.d("handling boot service action")
            }
        }
    }
}