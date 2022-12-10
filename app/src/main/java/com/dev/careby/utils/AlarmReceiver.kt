package com.dev.careby.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.dev.careby.R
import java.time.LocalTime

class AlarmReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent?) {
        try {
            showNotification(context, "Alarm Careby", "time", "10:00")
            TODO()
        }
        catch (e:java.lang.Exception){
            Log.d("Receive exception ", e.printStackTrace().toString())
        }



        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)
        Toast.makeText(context, "Вставай",Toast.LENGTH_LONG).show()

        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }

        val ringtone = RingtoneManager.getRingtone(context,alarmUri)
        ringtone.play()
    }
    private fun showNotification(context: Context,title: String, description: String, time: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "message_channel"
        val channelId = "message_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
        manager.notify(1,builder.build())
    }
}