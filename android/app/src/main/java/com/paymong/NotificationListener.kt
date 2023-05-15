package com.paymong

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import com.paymong.data.model.request.AddPayReqDto
import com.paymong.data.model.request.AddRoutineReqDto
import com.paymong.data.repository.MemberRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch

class NotificationListener : NotificationListenerService(), CapabilityClient.OnCapabilityChangedListener{

    companion object {
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
        private const val SAMSUNG_PAY_PACKAGE_NAME = "com.samsung.android.spay"
        private const val SAMSUNG_THINGS_PACKAGE_NAME = "com.samsung.android.oneconnect"
    }

    private var memberRepository:MemberRepository = MemberRepository()
    private lateinit var capabilityClient: CapabilityClient
    private lateinit var messageClient: MessageClient

    override fun onCreate() {
        super.onCreate()
        capabilityClient = Wearable.getCapabilityClient(this)
        messageClient = Wearable.getMessageClient(this)
        capabilityClient.addListener(this, CAPABILITY_WEAR_APP)

    }
    override fun onDestroy() {
        super.onDestroy()
        capabilityClient.removeListener(this, CAPABILITY_WEAR_APP)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        try {
            val packageName: String = sbn.packageName ?: ""
            val extras = sbn.notification.extras
            val title = extras.get(Notification.EXTRA_TEXT).toString()

            if (title == "null") return

            when(packageName){
                SAMSUNG_PAY_PACKAGE_NAME -> {
                    val message: List<String> = title.trim().split("₩")
                    val content : String = message[0].trim()
                    val price : Int = message[1].trim().replace(",", "").toInt()

                    CoroutineScope(Dispatchers.IO).launch {
                        memberRepository.addPay(AddPayReqDto(content, price))
                            .catch {
                                it.printStackTrace()
                            }
                            .collect{}
                    }
                }
                SAMSUNG_THINGS_PACKAGE_NAME-> {
                    val routine = title.trim().split("-")[0].trim()
                    CoroutineScope(Dispatchers.IO).launch {
                        memberRepository.addRoutine(AddRoutineReqDto(routine))
                            .catch {
                                it.printStackTrace()
                            }
                            .collect{}
                    }
                }
            }


        } catch (_: Exception) {}
    }

    override fun onCapabilityChanged(p0: CapabilityInfo) { }
}


