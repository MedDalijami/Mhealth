package com.example.mhealthcat.data.messages

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object CatMessageScheduler {
    private const val WORK_NAME = "cat_messages"

    fun schedule(context: Context) {
       val request = PeriodicWorkRequestBuilder<CatMessageWorker>(
           CatMessages.DAYS_BETWEEN_MSGS, TimeUnit.DAYS
       )
        .setInitialDelay(CatMessages.delayUntilNextMessage(CatMessages.MASSAGE_HOUR))
        .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancel (context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }

    //for testing purposes
    fun runNow(context: Context) {
        WorkManager.getInstance(context).enqueue(OneTimeWorkRequestBuilder<CatMessageWorker>().build())
    }

}