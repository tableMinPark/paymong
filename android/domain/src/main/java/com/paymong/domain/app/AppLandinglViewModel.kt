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
    private val dataApplicationRepository: DataApplicationRepository = DataApplicationRepository()

    var landingCode by mutableStateOf(LandingCode.LOADING)
    var wearNodesWithApp: Set<Node>? = null
    var allConnectedNodes: List<Node>? = null

    // 리프레시 토큰 로그인
    fun refreshLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                authRepository.reissue()
                    .catch {
                        it.printStackTrace()
                        landingCode = LandingCode.CANT_LOGIN
                    }
                    .collect { values ->
                        landingCode = if (values)
                            LandingCode.LOGIN_SUCCESS
                        else
                            LandingCode.LOGIN_FAIL
                    }
            } catch (_: Exception) {
                landingCode = LandingCode.CANT_LOGIN
            }
        }
    }
    // 웨어러블 최초 등록 여부 확인
    fun registCheck() {
        viewModelScope.launch {
            val watchId = dataApplicationRepository.getValue("watchId")

            if (watchId != "") {
                googlePlayLogin()
            } else {
                installCheck()
            }
        }
    }
    fun googlePlayLogin() {
        // 로그인 시도
        gamesSignInClient.signIn()
        // 로그인 리스너
        gamesSignInClient.isAuthenticated.addOnCompleteListener { isAuthenticatedTask: Task<AuthenticationResult> ->
            val isAuthenticated = isAuthenticatedTask.isSuccessful && isAuthenticatedTask.result.isAuthenticated
            // 로그인 성공
            if (isAuthenticated) {
                playersClient.currentPlayer.addOnCompleteListener { mTask: Task<Player?>? ->
                    val playerId = mTask?.result?.playerId.toString()
                    login(playerId)
                }
            }
            // 계정을 찾을 수 없음
            else {
                Toast.makeText(getApplication(),ToastMessage.LOGIN_ACCOUNT_NOT_FOUND.message, Toast.LENGTH_LONG).show()
                landingCode = LandingCode.CANT_LOGIN
            }
        }
    }
    fun googlePlayRegist() {
        // 로그인 시도
        gamesSignInClient.signIn()
        // 로그인 리스너
        gamesSignInClient.isAuthenticated.addOnCompleteListener { isAuthenticatedTask: Task<AuthenticationResult> ->
            val isAuthenticated = isAuthenticatedTask.isSuccessful && isAuthenticatedTask.result.isAuthenticated
            // 로그인 성공
            if (isAuthenticated) {
                playersClient.currentPlayer.addOnCompleteListener { mTask: Task<Player?>? ->
                    val playerId = mTask?.result?.playerId.toString()
                    registWearable(playerId)
                    login(playerId)
                }
            }
            // 계정을 찾을 수 없음
            else {
                landingCode = LandingCode.CANT_LOGIN
                Toast.makeText(getApplication(),ToastMessage.LOGIN_ACCOUNT_NOT_FOUND.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    // 로그인
    private fun login(playerId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                authRepository.login(LoginReqDto(playerId))
                    .catch {
                        landingCode = LandingCode.CANT_LOGIN
                    }
                    .collect { values ->
                        landingCode = if (values)
                            LandingCode.LOGIN_SUCCESS
                        else
                            LandingCode.CANT_LOGIN
                    }
            } catch (_: Exception) {
                landingCode = LandingCode.CANT_LOGIN
            }
        }
    }
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
//                wearableAppInstallRequest()
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }
    private fun wearableAppInstallRequest() {
        val wearNodesWithApp = wearNodesWithApp

        // 연결된 기기가 있고 설치된 경우
        if (!wearNodesWithApp.isNullOrEmpty()) {
            landingCode = LandingCode.HAS_WEARABLE_SUCCESS
        }
        // 연결된 웨어러블 기기에 앱이 설치되지 않은 경우
        else if (wearNodesWithApp != null && wearNodesWithApp.isEmpty()) {
            landingCode = LandingCode.HAS_WEARABLE_FAIL
        }
        // 연결된 기기가 없는경우 (최초 등록 여부는 앞에서 확인하기 때문에 여기까지 온 경우는 최초 등록 X, 기기없음의 경우)
        else if (wearNodesWithApp == null) {
            landingCode = LandingCode.REGIST_WEARABLE_FAIL
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
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    private fun registWearable(playerId: String) {
        viewModelScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes
                nodes.map { node ->
                    async {
                        if (node.id != "") {
                            dataApplicationRepository.setValue("watchId", node.id)
                            messageClient.sendMessage(
                                node.id,
                                START_WEAR_ACTIVITY_PATH,
                                playerId.toByteArray()
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