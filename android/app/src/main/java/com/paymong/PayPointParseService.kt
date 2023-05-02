package com.paymong

import android.R
import android.app.Notification
import android.app.Notification.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log


class PayPointParseService : Service() {

    val CHANNEL_ID = "PAYMONGCHANNEL"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_STICKY
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "알림 설정 모드 타이틀",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)!!
            manager!!.createNotificationChannel(serviceChannel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  )
        val notification: Notification = Builder(this, CHANNEL_ID)
            .setContentTitle("알림 타이틀")
            .setContentText("알림 설명")
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(100, notification)


        Thread { test() }.start()

        if(intent == null){
            return START_STICKY;
        }
        test();

        return super.onStartCommand(intent, flags, startId);
    }

    private fun test() {
        for (i in 0..99) {
            try {
                Thread.sleep(1000)
                Log.d("test", "count: $i")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}