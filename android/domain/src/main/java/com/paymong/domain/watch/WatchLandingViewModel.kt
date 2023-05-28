package com.paymong.domain.watch

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.wear.phone.interactions.PhoneTypeHelper
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.wearable.*
import com.paymong.common.code.LandingCode
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.repository.AuthRepository
import com.paymong.data.repository.DataApplicationRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.tasks.await

class WatchLandingViewModel(
    private var capabilityClient: CapabilityClient,
    private var remoteActivityHelper: RemoteActivityHelper,
    private val messageClient: MessageClient,
    application: Application
)  : AndroidViewModel(application) {
    companion object {
        private const val START_APP_ACTIVITY_PATH = "/start-activity"
        private const val CAPABILITY_PHONE_APP = "app_paymong"
        private const val ANDROID_MARKET_APP_URI = "market://details?id=com.paymong"
    }

    // 로그인 플래그
    private val authRepository: AuthRepository = AuthRepository()
    private val dataApplicationRepository = DataApplicationRepository()

    var landingCode by mutableStateOf(LandingCode.LOADING)
    var androidPhoneNodeWithApp: Node? = null

    // 로그인 시도 함수
    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            val playerId = dataApplicationRepository.getValue("playerId")

            // 초기 설정 됨
            if (playerId != "") {
                // 로그인 진행
                authRepository.watchLogin(LoginReqDto(playerId))
                    .catch {
                        landingCode = LandingCode.FAIL
                    }
                    .collect { data ->
                        landingCode = if (data) {
                            LandingCode.SUCCESS
                        } else LandingCode.FAIL
                    }
            }
            // 초기 설정 안됨
            else {
                // 설치 여부 확인
                checkIfPhoneHasApp()
            }
        }
    }
    // 모바일 앱 설치 여부 확인 함수
    fun checkIfPhoneHasApp() {
        viewModelScope.launch {
            try {
                val capabilityInfo = capabilityClient
                    .getCapability(CAPABILITY_PHONE_APP, CapabilityClient.FILTER_ALL)
                    .await()
                withContext(Dispatchers.Main) {
                    androidPhoneNodeWithApp = capabilityInfo.nodes.firstOrNull()
                    // 모바일 앱 설치 안됨
                    landingCode = if (androidPhoneNodeWithApp == null) {
                        LandingCode.NOT_INSTALL
                    } else {
                        LandingCode.NOT_CONFIG
                    }
                }
            } catch (_: Exception) {}
        }
    }
    // 모바일 앱 설치를 위해 원격으로 스토어를 실행하는 함수
    fun openAppInStoreOnPhone() {
        val intent = when (PhoneTypeHelper.getPhoneDeviceType(getApplication())) {
            PhoneTypeHelper.DEVICE_TYPE_ANDROID -> {
                Intent(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse(ANDROID_MARKET_APP_URI))
            }
            else -> {
                return
            }
        }

        viewModelScope.launch {
            try {
                remoteActivityHelper.startRemoteActivity(intent).await()
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (_: Exception) {}
        }
    }
    // 초기 설정을 위해 원격으로 모바일 앱 실행하는 함수
    fun openAppOnPhone() {
        viewModelScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(CAPABILITY_PHONE_APP, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes
                nodes.map { node ->
                    async {
                        messageClient.sendMessage(node.id,
                            START_APP_ACTIVITY_PATH, byteArrayOf())
                            .await()
                    }
                }.awaitAll()

            } catch (_: Exception) {}
        }
    }
}

class WatchLandingViewModelFactory(
    private var capabilityClient: CapabilityClient,
    private val remoteActivityHelper: RemoteActivityHelper,
    private val messageClient: MessageClient,
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WatchLandingViewModel::class.java)){
            return WatchLandingViewModel(capabilityClient, remoteActivityHelper, messageClient, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}