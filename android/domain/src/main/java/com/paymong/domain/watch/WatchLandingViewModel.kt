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
        private const val ANDROID_MARKET_APP_URI = "market://details?id=com.paymong&hl=en-US&ah=stDCGwZh4347ASlHN4muxnFaZwk"
    }

    // 로그인 플래그
    var loginState by mutableStateOf(LandingCode.LOADING)
    private val authRepository: AuthRepository = AuthRepository()
    private val dataApplicationRepository = DataApplicationRepository()

    var landingCode by mutableStateOf(LandingCode.LOADING)
    var androidPhoneNodeWithApp: Node? = null


    // 랜딩화면 로그인 확인
    fun loginCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.reissue()
                .catch {
                    loginState = LandingCode.LOGIN_FAIL
                }
                .collect {values ->
                    loginState = if (values)
                        LandingCode.LOGIN_SUCCESS
                    else
                        LandingCode.LOGIN_FAIL
                }
        }
    }
    fun installCheck() {
        viewModelScope.launch {
            launch {
                checkIfPhoneHasApp()
            }
        }
    }
    // 로그인
    private fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            val playerId = dataApplicationRepository.getValue("playerId")
            authRepository.watchLogin(LoginReqDto(playerId))
                .catch { loginState = LandingCode.LOGIN_FAIL }
                .collect { values ->
                    loginState = if (values)
                        LandingCode.LOGIN_SUCCESS
                    else
                        LandingCode.LOGIN_FAIL
                }
        }
    }
    private suspend fun checkIfPhoneHasApp() {
        try {
            val capabilityInfo = capabilityClient
                .getCapability(CAPABILITY_PHONE_APP, CapabilityClient.FILTER_ALL)
                .await()
            withContext(Dispatchers.Main) {
                androidPhoneNodeWithApp = capabilityInfo.nodes.firstOrNull()
                mobileAppInstallRequest()
            }
        } catch (_: CancellationException) {
        } catch (_: Throwable) {
        }
    }
    private fun mobileAppInstallRequest() {
        val androidPhoneNodeWithApp = androidPhoneNodeWithApp

        val dataApplicationRepository = DataApplicationRepository()
        val playId = dataApplicationRepository.getValue("playerId")

        if (playId == "") {
            // 폰 연결 O & 설치 안됨
            if (androidPhoneNodeWithApp == null) {
                landingCode = LandingCode.NOT_INSTALL
            }
            // 폰 연결 O & 설치 됨
            else {
                landingCode = LandingCode.INSTALL
            }
        } else {
            // 이미 한번 로그인한 사람
            login()
        }
    }
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
            } catch (throwable: Throwable) {
            }
        }
    }
    
    fun openAppOnPhone() {
        viewModelScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(CAPABILITY_PHONE_APP, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes

                // Send a message to all nodes in parallel
                nodes.map { node ->
                    async {
                        messageClient.sendMessage(node.id,
                            START_APP_ACTIVITY_PATH, byteArrayOf())
                            .await()
                    }
                }.awaitAll()

            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (_: Exception) {
            }
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