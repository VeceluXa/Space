package com.danilovfa.space.presentation.notifications

import android.app.PendingIntent
import android.content.Intent
import com.danilovfa.space.R
import com.danilovfa.space.presentation.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title ?: ""
        val text = message.notification?.body ?: ""

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationManager = NotificationManager(applicationContext)
        notificationManager.sendBasicNotification(
            title = title,
            text = text,
            icon = R.drawable.ic_launcher_foreground,
            pendingIntent = pendingIntent
        )
    }
}