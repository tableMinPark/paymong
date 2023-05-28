package com.paymong

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.paymong.data.model.request.AddPayReqDto
import com.paymong.data.model.request.AddRoutineReqDto
import com.paymong.data.repository.MemberRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch

class NotificationListener : NotificationListenerService() {
    companion object {
        private const val SAMSUNG_PAY_PACKAGE_NAME = "com.samsung.android.spay"
        private const val SAMSUNG_THINGS_PACKAGE_NAME = "com.samsung.android.oneconnect"
    }

    private var memberRepository:MemberRepository = MemberRepository()

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        try {
            val packageName: String = sbn.packageName ?: ""
            val extras = sbn.notification.extras
            val title = extras.get(Notification.EXTRA_TITLE).toString()

            if (title == "null") return

            when(packageName){
                SAMSUNG_PAY_PACKAGE_NAME
                -> {
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


        } catch (e: Exception) {e.printStackTrace()}
    }
}


