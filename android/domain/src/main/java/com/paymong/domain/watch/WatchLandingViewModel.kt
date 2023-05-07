package com.paymong.domain.watch

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.wear.phone.interactions.PhoneTypeHelper
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Node
import com.paymong.common.code.LandingCode
import com.paymong.common.code.ToastMessage
import com.paymong.data.repository.DataApplicationRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class WatchLandingViewModel(
    private var capabilityClient: CapabilityClient,
    private var remoteActivityHelper: RemoteActivityHelper,
    application: Application
)  : AndroidViewModel(application) {
    companion object {
        private const val CAPABILITY_PHONE_APP = "app_paymong"
        private const val ANDROID_MARKET_APP_URI = "market://details?id=com.nhn.android.search&hl=ko"
    }

    var landingCode by mutableStateOf(LandingCode.LOADING)
    var androidPhoneNodeWithApp: Node? = null

    fun installCheck() {
        viewModelScope.launch {
            launch {
                checkIfPhoneHasApp()
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
        } catch (cancellationException: CancellationException) {
        } catch (throwable: Throwable) {
        }
    }
    private fun mobileAppInstallRequest() {
        val androidPhoneNodeWithApp = androidPhoneNodeWithApp

        val dataApplicationRepository = DataApplicationRepository()
        val playId = dataApplicationRepository.getValue("playerId")

        if (playId == "") {
            // 폰 연결 O & 설치 안됨
            if (androidPhoneNodeWithApp == null) {
                // 설치 진행
                landingCode = LandingCode.NOT_INSTALL
            }
            // 폰 연결 O & 설치 됨
            else {

                // playerId 받아옴
                landingCode = LandingCode.VALID
            }
        } else {
            // 이미 한번 로그인한 사람
            landingCode = LandingCode.VALID
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
}

class WatchLandingViewModelFactory(
    private var capabilityClient: CapabilityClient,
    private val remoteActivityHelper: RemoteActivityHelper,
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WatchLandingViewModel::class.java)){
            return WatchLandingViewModel(capabilityClient, remoteActivityHelper, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}