package com.junsu.sample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.junsu.sample.service.BootService
import timber.log.Timber

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(c: Context?, i: Intent?) {
        Timber.d("!!!action: ${i?.action}")
        when (i?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                c?.also {
                    BootService.start(it)
                }
            }
        }
    }
}