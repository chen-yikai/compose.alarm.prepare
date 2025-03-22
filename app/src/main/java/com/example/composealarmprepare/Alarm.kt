package com.example.composealarmprepare

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.nio.BufferUnderflowException

@RequiresApi(Build.VERSION_CODES.O)
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = (100..300).random()
        val channelId = "alarm_notification"
        val channel = NotificationChannel(
            channelId,
            "Alarm Notification",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setContentText("Time to listen some music")
            .setContentTitle("Listen reminder")
            .setSmallIcon(
                R.drawable.ic_launcher_foreground
            ).build()
        notificationManager.notify(notificationId, notification)
    }
}

@SuppressLint("ScheduleExactAlarm")
fun setExactAlarm(context: Context, triggerTime: Long) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

//    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        triggerTime,
        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
        pendingIntent
    )
}
