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
import kotlinx.coroutines.tasks.await

class NotificationListener : NotificationListenerService(), CapabilityClient.OnCapabilityChangedListener{

    companion object {
        private const val START_WEAR_ACTIVITY_PATH = "/thingsAlarm"
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
        private const val SAMSUNG_PAY_PACKAGE_NAME = "com.samsung.android.spay"
        private const val SAMSUNG_THINGS_PACKAGE_NAME = "com.samsung.android.oneconnect"
    }

//    private var memberRepository:MemberRepository = MemberRepository()
//    private lateinit var capabilityClient: CapabilityClient
//    private lateinit var messageClient: MessageClient

<<<<<<< HEAD
    private fun thingsAlarm(thingsCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val nodes = capabilityClient
                    .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes

                // Send a message to all nodes in parallel
                nodes.map { node ->
                    async {
                        if (node.id != "") {
                            messageClient.sendMessage(
                                node.id,
                                START_WEAR_ACTIVITY_PATH,
                                thingsCode.toByteArray()
                            )
                                .await()
                        }
                    }
                }.awaitAll()
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
//        capabilityClient = Wearable.getCapabilityClient(this)
//        messageClient = Wearable.getMessageClient(this)
//        capabilityClient.addListener(this, CAPABILITY_WEAR_APP)

    }
    override fun onDestroy() {
        super.onDestroy()
//        capabilityClient.removeListener(this, CAPABILITY_WEAR_APP)
    }


    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)


    }

    override fun onCapabilityChanged(p0: CapabilityInfo) {
    }
}


