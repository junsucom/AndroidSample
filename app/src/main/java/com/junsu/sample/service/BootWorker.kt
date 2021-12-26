package com.junsu.sample.service

import android.content.Context
import androidx.work.*
import timber.log.Timber

/**
 * WorkManager basics
 * https://medium.com/androiddevelopers/workmanager-basics-beba51e94048
 */
class BootWorker (appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    companion object {
        private const val KEY_TEMP_MESSAGE = "keyTempMessage"
        fun start(context: Context, tempMessage: String) {
            val imageData = workDataOf(KEY_TEMP_MESSAGE to tempMessage)

            val bootWorkRequest: WorkRequest =
                OneTimeWorkRequestBuilder<BootWorker>()
                    .setInputData(imageData)
                    .build()
            WorkManager
                .getInstance(context)
                .enqueue(bootWorkRequest)
        }
    }

    override fun doWork(): Result {
        Timber.d("BootWorker ${inputData.getString(KEY_TEMP_MESSAGE)} Thread:${Thread.currentThread().name}")
        return Result.success()
    }
}