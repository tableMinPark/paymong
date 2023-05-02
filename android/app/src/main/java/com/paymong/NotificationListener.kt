package com.paymong

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast
import java.time.LocalDateTime

class NotificationListener : NotificationListenerService() {
    private var start = LocalDateTime.now()
    init {
        start = LocalDateTime.now()
    }
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val extras = sbn?.notification?.extras
        val packageName: String = sbn?.packageName ?: ""
        val message: List<String> = extras?.get(Notification.EXTRA_TITLE).toString().split("\\")

        val content : String = message[0]
        val price : Int = message[1].toInt()

        Log.e("samsung pay", "$packageName : $content : $price : $start")
        Toast.makeText(applicationContext, "$content : $price : $start", Toast.LENGTH_LONG).show()
    }
}


