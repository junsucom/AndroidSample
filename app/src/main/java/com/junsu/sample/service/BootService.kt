package com.junsu.sample.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.junsu.sample.R
import timber.log.Timber


class BootService : IntentService(TAG) {
    companion object {
        private const val TAG = "BootService"
        private const val ACTION_BOOT_SERVICE = "com.junsu.sample.service.actionBootService"
        private const val NOTIFICATION_CHANNEL_ID = "com.junsu.sample"
        private const val NOTIFICATION_CHANNEL_NAME = "BootService"
        private const val SERVICE_CHANNEL_ID = 3000
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

    override fun onCreate() {
        makeNotification()
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        channelName: String,
        f: (NotificationChannel) -> Unit = {}
    ): String {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        f(channel)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channelId
    }

    private fun makeNotification() {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME)
            } else {
                ""
            }

        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("running...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        val notification = builder.build()

        startForeground(SERVICE_CHANNEL_ID, notification)
    }

    override fun onHandleIntent(intent: Intent?) {
        Timber.d("${intent?.action}")
        when (intent?.action) {
            ACTION_BOOT_SERVICE -> {
                Thread.sleep(5000)
                Timber.d("handling boot service action")
            }
        }
    }
}