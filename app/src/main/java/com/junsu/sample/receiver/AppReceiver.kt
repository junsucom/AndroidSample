package com.junsu.sample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.junsu.sample.service.BootWorker
import timber.log.Timber

class AppReceiver: BroadcastReceiver() {
    companion object {
        const val ACTION_SNOOZE = "com.junsu.sample.ActionSnooze"
    }
    override fun onReceive(c: Context?, i: Intent?) {
        Timber.d("!!!action: ${i?.action}")
        when (i?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                c?.also {
                    BootWorker.start(it, "Started by BootReceiver")
                }
            }
            ACTION_SNOOZE -> {

            }
        }
    }
}