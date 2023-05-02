package com.paymong

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log


class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if ("android.intent.action.BOOT_COMPLETED" == intent.action) {
            Log.d("background", "백그라운드 실행")
            val payPointParseService = Intent(context, PayPointParseService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(payPointParseService)
            } else {
                context.startService(payPointParseService)
            }
        }
    }
}
