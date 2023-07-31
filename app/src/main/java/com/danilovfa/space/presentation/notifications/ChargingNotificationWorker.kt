package com.danilovfa.space.presentation.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.danilovfa.space.R
import com.danilovfa.space.presentation.ui.MainActivity
import com.danilovfa.space.utils.Constants

class ChargingNotificationWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val notificationManager = NotificationManager(context)

        Log.d("MyWorker", "sendNotification")

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(EXTRA_TAB_CONTAINER_ID, Constants.MAP_TAB_ID)
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        notificationManager.sendNotificationWithActionButton(
            title = context.getString(R.string.push_notification_title),
            text = context.getString(R.string.push_notification_text_charged),
            icon = R.drawable.ic_launcher_foreground,
            pendingIntent = pendingIntent
        )
    }

    companion object {
        const val EXTRA_TAB_CONTAINER_ID = "EXTRA_TAB_CONTAINER_ID"
    }
}