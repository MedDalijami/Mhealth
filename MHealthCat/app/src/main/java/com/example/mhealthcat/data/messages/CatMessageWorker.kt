package com.example.mhealthcat.data.messages

import android.content.Context
import androidx.core.content.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

// Runs in the background to show a cat message notification
class CatMessageWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val preferences = applicationContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val lastIndex = preferences.getInt(KEY_LAST_INDEX, -1)

        val index = CatMessages.pickIndex(lastIndex)
        CatMessageNotifications.show(applicationContext, CatMessages.listOfMessages[index])

        preferences.edit { putInt(KEY_LAST_INDEX, index) }
        return Result.success()
    }

    private companion object {
        const val PREFERENCES = "cat_messages"
        const val KEY_LAST_INDEX = "last_index"
    }
}