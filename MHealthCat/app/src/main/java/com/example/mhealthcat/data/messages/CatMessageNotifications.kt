package com.example.mhealthcat.data.messages

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.mhealthcat.MainActivity
import com.example.mhealthcat.R


object CatMessageNotifications {
    private const val CHANNEL_ID = "cat_messages"
    private const val NOTIFICATION_ID = 1

    private fun createChannel (context: Context) {
        val channel = NotificationChannel (
            CHANNEL_ID,
            "Sporočila mucke",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply { 
            description = "Sporočila mucke, ki te spodbujajo k skrbi zase."
        }
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    fun show(context: Context, msg: CatMsg) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) return

        createChannel(context)

        val openApp = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder (context, CHANNEL_ID)
            .setSmallIcon(R.drawable.muckalogo)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.cat_logo))
            .setContentTitle(msg.title)
            .setContentText(msg.message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(msg.message))
            .setContentIntent(openApp)
            .setAutoCancel(true)

        notification.build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification.build())
    }
}