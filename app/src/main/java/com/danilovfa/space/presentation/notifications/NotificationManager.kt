package com.danilovfa.space.presentation.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.danilovfa.space.R
import com.danilovfa.space.utils.Constants.Companion.NOTIFICATION_CHANNEL_ID
import com.danilovfa.space.utils.Constants.Companion.PUSH_NOTIFICATION_ID

class NotificationManager(private val context: Context) {

    fun sendBasicNotification(
        title: String,
        text: String,
        @DrawableRes icon: Int,
        pendingIntent: PendingIntent
    ) {
        val notification = NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        notify(
            notification,
            PUSH_NOTIFICATION_ID
        )
    }

    fun sendNotificationWithActionButton(
        title: String,
        text: String,
        @DrawableRes icon: Int,
        pendingIntent: PendingIntent
    ) {
        val notification = NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(
                R.drawable.baseline_travel_explore_24,
                context.getString(R.string.push_notification_action),
                pendingIntent
            )

        notify(
            notification,
            PUSH_NOTIFICATION_ID
        )
    }

    private fun notify(
        notification: NotificationCompat.Builder,
        channelId: Int = PUSH_NOTIFICATION_ID,
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat
                .from(context)
                .notify(channelId, notification.build())
        }
    }

    // Call only in Activity
    fun setupPermissions() {
        if (context is AppCompatActivity) {
            askForNotificationPermissions(context)
            createNotificationChannel()
        }
    }

    private fun askForNotificationPermissions(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, name, importance
            ).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}