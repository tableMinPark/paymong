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
        private const val PLAY_STORE_APP_URI = "market://details?id=com.nhn.android.search&hl=ko"
    }

    private val authRepository: AuthRepository = AuthRepository()
    private val dataApplicationRepository: DataApplicationRepository = DataApplicationRepository()

    var landingCode by mutableStateOf(LandingCode.LOADING)
    var wearNodesWithApp: Set<Node>? = null
    var allConnectedNodes: List<Node>? = null

    init {
    }

    // 리프레시 토큰 로그인
    fun refreshLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.reissue()
                .catch {
                    landingCode = LandingCode.LOGIN_FAIL
                }
                .collect {values ->
                    Log.e("refreshLogin()", values.toString())
                    landingCode = if (values)
                        LandingCode.LOGIN_SUCCESS
                    else
                        LandingCode.LOGIN_FAIL
                }
        }
    }
    // 웨어러블 최초 등록 여부 확인
    fun registCheck() {
        viewModelScope.launch {
            val watchId = dataApplicationRepository.getValue("watchId")
            Log.e("registCheck()", watchId)

            if (watchId != "") {
                Log.e("registCheck()", "REGIST_WEARABLE_SUCCESS")
                landingCode = LandingCode.REGIST_WEARABLE_SUCCESS
            } else {
                Log.e("registCheck()", "REGIST_WEARABLE_FAIL")
                landingCode = LandingCode.REGIST_WEARABLE_FAIL
                // 등록하지 않았으면 등록할 수 있는 기기 있는지 확인
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
                Toast.makeText(
                    getApplication(),
                    ToastMessage.LOGIN_ACCOUNT_NOT_FOUND.message,
                    Toast.LENGTH_LONG
                ).show()
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
                Toast.makeText(
                    getApplication(),
                    ToastMessage.LOGIN_ACCOUNT_NOT_FOUND.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    // 로그인
    private fun login(playerId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(LoginReqDto(playerId))
                .catch { landingCode = LandingCode.LOGIN_FAIL }
                .collect { values ->
                    Log.d("login()", values.toString())
                    landingCode = if (values)
                        LandingCode.LOGIN_SUCCESS
                    else
                        LandingCode.LOGIN_FAIL
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
                wearableAppInstallRequest()
            }
        } catch (cancellationException: CancellationException) {
        } catch (throwable: Throwable) {
        }
    }
    private fun wearableAppInstallRequest() {
        val wearNodesWithApp = wearNodesWithApp
        // 연결된 기기가 있고 설치된 경우
        if (wearNodesWithApp != null && wearNodesWithApp.isNotEmpty()) {
            landingCode = LandingCode.HAS_WEARABLE_SUCCESS
        }
        // 연결된 웨어러블 기기에 앱이 설치되지 않은 경우
        else if (wearNodesWithApp != null && wearNodesWithApp.isEmpty()) {
            Log.d("wearableAppInstallRequest()", "HAS_WEARABLE_FAIL")
            landingCode = LandingCode.HAS_WEARABLE_FAIL
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
    fun registWearable(playerId: String) {
        Log.e("registWearable()", "start")
        viewModelScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(CAPABILITY_WEAR_APP, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes

                // Send a message to all nodes in parallel
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

                Log.d("registWearable()", "Starting activity requests sent successfully")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d("registWearable()", "Starting activity failed: $exception")
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