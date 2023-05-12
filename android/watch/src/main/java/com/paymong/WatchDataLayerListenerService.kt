package com.paymong

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.paymong.data.repository.DataApplicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class WatchDataLayerListenerService : WearableListenerService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val dataApplicationRepository: DataApplicationRepository = DataApplicationRepository()

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        Log.d("수신테스트",messageEvent.path )
        when (messageEvent.path) {
            START_WEAR_ACTIVITY_PATH -> {
                val playerId = messageEvent.data.decodeToString()
                Log.d("DataLayerListenerService", playerId)
                dataApplicationRepository.setValue("playerId", playerId)
                startActivity(
                    Intent(this, WatchMainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
            THINGS_ALARM ->{
                val thingsCode = messageEvent.data.decodeToString()
                Log.d("thingsCode 수신", thingsCode)
                Toast.makeText(this, "싱스알람!!." + thingsCode, Toast.LENGTH_SHORT).show()
                /*val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // 특정 동작 수행
                }, 10000)*/
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val START_WEAR_ACTIVITY_PATH = "/start-activity"
        private const val THINGS_ALARM = "/thingsAlarm"
    }
}
