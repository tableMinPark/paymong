package com.paymong

import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundService : Service() {
    companion object {
        const val CHANNEL_ID = "PaymongForegroundServiceChannel"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notificationIntent = Intent(this, AppMainActivity::class.java)
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification : Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Paymong")
            .setContentText("SamsungPay, SamsungThings 알림 수집 서비스")
            .setSmallIcon(com.paymong.common.R.drawable.app_logo)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)

        object : Thread("Notification Parse Thread") {
            override fun run() {
                super.run()
                NotificationListener()
            }
        }.start()

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Paymong Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}