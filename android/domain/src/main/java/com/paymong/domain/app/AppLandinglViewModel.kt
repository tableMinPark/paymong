package com.paymong.domain.app

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.games.AuthenticationResult
import com.google.android.gms.games.GamesSignInClient
import com.google.android.gms.games.Player
import com.google.android.gms.games.PlayersClient
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.*
import com.paymong.common.code.LandingCode
import com.paymong.common.code.ToastMessage
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.repository.AuthRepository
import com.paymong.data.repository.DataApplicationRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.tasks.await

class AppLandinglViewModel(
    private var capabilityClient: CapabilityClient,
    private var nodeClient: NodeClient,
    private var remoteActivityHelper: RemoteActivityHelper,
    private var messageClient: MessageClient,
    private var gamesSignInClient : GamesSignInClient,
    private var playersClient: PlayersClient,
    application: Application
)  : AndroidViewModel(application) {
    companion object {
        private const val START_WEAR_ACTIVITY_PATH = "/start-activity"
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
        private const val PLAY_STORE_APP_URI = "market://details?id=com.paymong"
    }

    private val authRepository: AuthRepository = AuthRepository()

    var landingCode by mutableStateOf(LandingCode.LOADING)
    var playerId by mutableStateOf("")
    var wearNodesWithApp: Set<Node>? = null
    private var allConnectedNodes: List<Node>? = null

    // 구글 플레이 로그인 (playerId 획득)
    fun googlePlayLogin() {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d("landing", "googlePlayLogin - start")
            // 구글 로그인
            gamesSignInClient.signIn()
            // 로그인 리스너
            gamesSignInClient.isAuthenticated.addOnCompleteListener { isAuthenticatedTask: Task<AuthenticationResult> ->
                val isAuthenticated = isAuthenticatedTask.isSuccessful && isAuthenticatedTask.result.isAuthenticated
                // 로그인 성공
                if (isAuthenticated) {
                    playersClient.currentPlayer.addOnCompleteListener { mTask: Task<Player?>? ->
                        playerId = mTask?.result?.playerId.toString()
                        login()
                    }
                }
                // 계정을 찾을 수 없음
                else {
                    landingCode = LandingCode.LOGIN_FAIL
                }
            }
            Log.d("landing", "googlePlayLogin - end / landingCode : $landingCode")
        }
    }
    // 페이몽 서버 로그인 (playerId를 이용해 페이몽 서버 로그인 - 토큰 획득)
    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("landing", "login - start")
            try {
                authRepository.login(LoginReqDto(playerId))
                    .catch { landingCode = LandingCode.LOGIN_FAIL }
                    .collect { data ->
                        if (data) {
                            checkRegisterWearable()
                        } else {
                            landingCode = LandingCode.LOGIN_FAIL
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Log.d("landing", "login - end / landingCode : $landingCode")
        }
    }
    // 웨어러블 기기 등록 확인
    private fun checkRegisterWearable() {
        Log.d("landing", "checkRegisterWearable - start")
        val watchId = DataApplicationRepository().getValue("watchId")
        if (watchId != "") {
            landingCode = LandingCode.SUCCESS
        } else {
            // 기기 보유 여부, 설치 여부 확인
            checkWearable()
        }
        Log.d("landing", "checkRegisterWearable - end / landingCode : $landingCode")
    }
    // 웨어러블 등록 여부 (근처에 있는지), 웨어러블 기기에 앱 설치 여부 확인 함수
    fun checkWearable() {
        viewModelScope.launch {
            Log.d("landing", "checkWearable - start")
            launch {
                findWearDevicesWithApp()
            }
            launch {
                findAllWearDevices()
            }
        }
    }
    // 웨어러블 등록 여부 (근처에 있는지)
    private suspend fun findWearDevicesWithApp() {
        try {
            val capabilityInfo = capabilityClient
                .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_ALL)
                .await()

            withContext(Dispatchers.Main) {
                wearNodesWithApp = capabilityInfo.nodes
                wearableAppInstallRequest()
            }
        } catch (_: Exception) {}
    }
    // 웨어러블 기기에 앱 설치 여부 확인 함수
    private suspend fun findAllWearDevices() {
        try {
            val connectedNodes = nodeClient.connectedNodes.await()
            withContext(Dispatchers.Main) {
                allConnectedNodes = connectedNodes
                wearableAppInstallRequest()
            }
        } catch (_: Exception) {}
    }
    private fun wearableAppInstallRequest() {
        val wearNodesWithApp = wearNodesWithApp
        val wearNodes = allConnectedNodes

        if (wearNodesWithApp == null || wearNodes == null)
            return

        // 연결된 기기가 있고 설치된 경우
        if (wearNodesWithApp.isNotEmpty() && wearNodes.isNotEmpty()) {
            landingCode = LandingCode.NOT_CONFIG
        }
        // 연결된 웨어러블 기기에 앱이 설치되지 않은 경우
        else if (wearNodesWithApp.isEmpty() && wearNodes.isNotEmpty()) {
            landingCode = LandingCode.NOT_INSTALL
        }
        // 연결된 기기가 없는경우 (최초 등록 여부는 앞에서 확인하기 때문에 여기까지 온 경우는 최초 등록 X, 기기없음의 경우)
        else if (wearNodes.isEmpty()) {
            landingCode = LandingCode.NOT_HAS_WEARABLE
        }
    }
    // 웨어러블 앱 설치를 위해 원격으로 스토어를 실행하는 함수
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
                } catch (_: Exception) {}
            }
        }
    }
    // 초기 설정을 위해 원격으로 웨어러블 앱 실행하는 함수
    fun registerWearable() {
        viewModelScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes
                nodes.map { node ->
                    async {
                        if (node.id != "") {
                            DataApplicationRepository().setValue("watchId", node.id)
                            messageClient.sendMessage(
                                node.id,
                                START_WEAR_ACTIVITY_PATH,
                                playerId.toByteArray()
                            )
                                .await()
                        }
                    }
                }.awaitAll()
                landingCode = LandingCode.SUCCESS
            } catch (_: Exception) {}
        }
    }
}

class AppLandingViewModelFactory(
    private val capabilityClient: CapabilityClient,
    private val nodeClient: NodeClient,
    private val remoteActivityHelper: RemoteActivityHelper,
    private val messageClient: MessageClient,
    private val gamesSignInClient : GamesSignInClient,
    private val playersClient: PlayersClient,
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AppLandinglViewModel::class.java)){
            return AppLandinglViewModel(capabilityClient, nodeClient, remoteActivityHelper, messageClient, gamesSignInClient, playersClient, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}