package com.example.analogueclock.utility

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import androidx.core.app.NotificationManagerCompat
import android.content.Intent
import com.example.analogueclock.activity.AlarmActivity
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.analogueclock.R
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import java.lang.Exception

class AlarmReceiver : BroadcastReceiver() {
    private var notificationManager: NotificationManagerCompat? = null

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val a = AlarmActivity()
        val myIntent: Intent?
        myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(""))
        context.startForegroundService(intent)
        Log.d("Debug", "on Receive")
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        Log.d("Debug", "on Receive intent")
        val notificationBuilder = NotificationCompat.Builder(context, "channel_1")
        notificationBuilder
            .setContentTitle("Alarm")
            .setContentText(a.alarmTime)
            .setTicker("Notification!")
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
        notificationManager = NotificationManagerCompat.from(context)
        notificationManager!!.notify(1, notificationBuilder.build())
        val rt = RingtoneManager.getRingtone(
            context,
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        )
        rt.play()
        try {
            Thread.sleep(10000)
            rt.stop()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}