package com.paymong

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.games.GamesSignInClient
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.PlayGamesSdk
import com.google.android.gms.games.PlayersClient
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.Wearable
import com.paymong.common.code.SocketCode
import com.paymong.data.repository.DataApplicationRepository
import com.paymong.domain.app.AppLandingViewModelFactory
import com.paymong.domain.app.AppLandinglViewModel
import com.paymong.domain.app.AppViewModel
import com.paymong.domain.app.AppViewModelFactory
import com.paymong.ui.app.AppMain

class AppMainActivity : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {
    companion object {
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
    }

    // 모바일-워치 간 연결
    private lateinit var capabilityClient: CapabilityClient
    private lateinit var nodeClient: NodeClient
    private lateinit var remoteActivityHelper: RemoteActivityHelper
    private lateinit var messageClient: MessageClient
    // google-play-games
    private lateinit var gamesSignInClient : GamesSignInClient
    private lateinit var playersClient: PlayersClient
    // 로그인을 위한 viewModel
    private lateinit var appLandingViewModelFactory : AppLandingViewModelFactory
    private lateinit var appLandinglViewModel : AppLandinglViewModel
    // appViewModel
    private lateinit var appViewModelFactory: AppViewModelFactory
    private lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 필수 권한 확인
        if (!isNotificationPermissionGranted()) startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))

        // 모바일-워치 간 연결
        capabilityClient = Wearable.getCapabilityClient(this)
        nodeClient = Wearable.getNodeClient(this)
        remoteActivityHelper = RemoteActivityHelper(this)
        messageClient = Wearable.getMessageClient(this)
        // google-play-games
        PlayGamesSdk.initialize(this)
        gamesSignInClient = PlayGames.getGamesSignInClient(this)
        playersClient = PlayGames.getPlayersClient(this)
        // 로그인을 위한 viewModel
        appLandingViewModelFactory = AppLandingViewModelFactory(capabilityClient, nodeClient, remoteActivityHelper, messageClient, gamesSignInClient, playersClient, this.application)
        appLandinglViewModel = ViewModelProvider(this@AppMainActivity, appLandingViewModelFactory)[AppLandinglViewModel::class.java]
        // appViewModel
        appViewModelFactory = AppViewModelFactory(this.application)
        appViewModel = ViewModelProvider(this@AppMainActivity, appViewModelFactory)[AppViewModel::class.java]
        // 포그라운드 서비스 실행
        val serviceIntent = Intent(this, ForegroundService::class.java)
        this.startForegroundService(serviceIntent)

        setContent {
            AppMain(appLandinglViewModel)
        }
    }
    // 앱 중지 마다 리스너 해제
    override fun onPause() {
        super.onPause()
        try {
            capabilityClient.removeListener(this, CAPABILITY_WEAR_APP)
            if (appViewModel.socketState == SocketCode.CONNECT) {
                Thread.sleep(1000)
                appViewModel.disConnectSocket()
            }
        } catch (_: Exception) {}
    }
    override fun onResume() {
        super.onResume()
        try {
            // 리스너
            capabilityClient.addListener(this, CAPABILITY_WEAR_APP)
            if (appViewModel.socketState == SocketCode.DISCONNECT) {
                appViewModel.socketConnect()
            }
        } catch (_: Exception) {}
    }
    // 웨어러블 연결 여부 확인해서 연결 때마다 앱 설치 여부 확인
    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        if (DataApplicationRepository().getValue("watchId") == "") {
            appLandinglViewModel.checkWearable()
        }
    }
    // 필요 권한 동의 확인 함수
    private fun isNotificationPermissionGranted(): Boolean {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.isNotificationListenerAccessGranted(ComponentName(application, NotificationListener::class.java))
        } else {
            NotificationManagerCompat.getEnabledListenerPackages(applicationContext).contains(applicationContext.packageName)
        }
    }
}
