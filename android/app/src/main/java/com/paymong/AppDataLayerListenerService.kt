package com.paymong

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.paymong.data.repository.DataApplicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class AppDataLayerListenerService : WearableListenerService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val dataApplicationRepository: DataApplicationRepository = DataApplicationRepository()

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

        Log.e("AppDataLayerListenerService()", "오냐?")
        when (messageEvent.path) {
            START_APP_ACTIVITY_PATH -> {
                Log.e("AppDataLayerListenerService()", "재시작")
                dataApplicationRepository.setValue("accessToken", "")
                dataApplicationRepository.setValue("refreshToken", "")
                dataApplicationRepository.setValue("watchId", "")
                startActivity(
                    Intent(this, AppMainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val START_APP_ACTIVITY_PATH = "/start-activity"
    }
}
