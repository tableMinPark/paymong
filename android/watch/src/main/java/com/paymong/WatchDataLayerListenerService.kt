package com.paymong

import android.content.Intent
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
        when (messageEvent.path) {
            START_WEAR_ACTIVITY_PATH -> {
                val playerId = messageEvent.data.decodeToString()
                dataApplicationRepository.setValue("accessToken", "")
                dataApplicationRepository.setValue("refreshToken", "")
                dataApplicationRepository.setValue("playerId", playerId)
                startActivity(
                    Intent(this, WatchMainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val START_WEAR_ACTIVITY_PATH = "/start-activity"
    }
}
