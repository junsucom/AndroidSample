/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.junsu.sample.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.junsu.sample.MainActivity
import com.junsu.sample.R
import com.junsu.sample.receiver.AppReceiver

// Notification ID.
private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0
private const val FLAGS = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // Create the content intent for the notification, which launches
    // this activity

    // create intent
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    // create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        FLAGS
    )

    // add style
    val eggImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ic_launcher_foreground
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImage)
        .bigLargeIcon(null)

    // add snooze action
    val snoozeIntent = Intent(applicationContext, AppReceiver::class.java).apply {
        action = AppReceiver.ACTION_SNOOZE
    }
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        REQUEST_CODE,
        snoozeIntent,
        FLAGS)

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    ).apply {
        // set title, text and icon to builder
        setSmallIcon(R.drawable.ic_baseline_notifications_24)
        setContentTitle(
            applicationContext
                .getString(R.string.notification_title)
        )
        setContentText(messageBody)

        // set content intent
        setContentIntent(contentPendingIntent)
        setAutoCancel(true)

        // add style to builder
        setStyle(bigPicStyle)
        setLargeIcon(eggImage)

        // add snooze action
        addAction(
            R.drawable.ic_baseline_snooze_24,
            applicationContext.getString(R.string.snooze),
            snoozePendingIntent
        )

        // set priority
        priority = NotificationCompat.PRIORITY_HIGH
    }
    // call notify
    notify(NOTIFICATION_ID, builder.build())
}

// Cancel all notifications
/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
