package com.paymong.domain.app

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.NodeClient
import com.paymong.common.code.LandingCode
import com.paymong.common.code.ToastMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AppInstallViewModel(
    private var capabilityClient: CapabilityClient,
    private var nodeClient: NodeClient,
    private var remoteActivityHelper: RemoteActivityHelper,
    application: Application
)  : AndroidViewModel(application) {
    companion object {
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
        private const val PLAY_STORE_APP_URI = "market://details?id=com.nhn.android.search&hl=ko"
    }

    var isInstall by mutableStateOf(LandingCode.LOADING)
    var wearNodesWithApp: Set<Node>? = null
    var allConnectedNodes: List<Node>? = null

    fun installCheck() {
        viewModelScope.launch {
            launch {
                findWearDevicesWithApp()
            }
            launch {
                findAllWearDevices()
            }
        }
    }
    private suspend fun findWearDevicesWithApp() {
        try {
            val capabilityInfo = capabilityClient
                .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_ALL)
                .await()

            withContext(Dispatchers.Main) {
                wearNodesWithApp = capabilityInfo.nodes
                wearableAppInstallRequest()
            }
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (throwable: Throwable) {
        }
    }
    private suspend fun findAllWearDevices() {
        try {
            val connectedNodes = nodeClient.connectedNodes.await()
            withContext(Dispatchers.Main) {
                allConnectedNodes = connectedNodes
                wearableAppInstallRequest()
            }
        } catch (cancellationException: CancellationException) {
        } catch (throwable: Throwable) {
        }
    }
    private fun wearableAppInstallRequest() {
        val wearNodesWithApp = wearNodesWithApp
        // 연결된 웨어러블 기기에 앱이 설치되지 않은 경우
        if (wearNodesWithApp != null && wearNodesWithApp.isEmpty()) {
            isInstall = LandingCode.NOT_INSTALL
            // 와치에 설치할 수 있도록 설치 페이지 띄울 수 있는 요청 보냄
            Toast.makeText(
                getApplication(),
                ToastMessage.WEARABLE_INSTALL_REQUEST.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        if (wearNodesWithApp != null && wearNodesWithApp.isNotEmpty()) {
            isInstall = LandingCode.INSTALL
            Toast.makeText(
                getApplication(),
                ToastMessage.WEARABLE_INSTALL_SUCCESS.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun openPlayStoreOnWearDevicesWithoutApp() {
        val wearNodesWithApp = wearNodesWithApp ?: return
        val allConnectedNodes = allConnectedNodes ?: return
        val nodesWithoutApp = allConnectedNodes - wearNodesWithApp
        val intent = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.parse(PLAY_STORE_APP_URI))
        nodesWithoutApp.forEach { node ->
            viewModelScope.launch {
                try {
                    remoteActivityHelper
                        .startRemoteActivity(
                            targetIntent = intent,
                            targetNodeId = node.id
                        )
                        .await()
                } catch (cancellationException: CancellationException) {
                } catch (throwable: Throwable) {
                    Toast.makeText(
                        getApplication(),
                        ToastMessage.INSTALL_FAIL.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

class AppInstallViewModelFactory(
    private var capabilityClient: CapabilityClient,
    private var nodeClient: NodeClient,
    private val remoteActivityHelper: RemoteActivityHelper,
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AppInstallViewModel::class.java)){
            return AppInstallViewModel(capabilityClient, nodeClient, remoteActivityHelper, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}