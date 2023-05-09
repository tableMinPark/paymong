package com.paymong

import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.games.GamesSignInClient
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.PlayGamesSdk
import com.google.android.gms.games.PlayersClient
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataItem
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import com.paymong.data.repository.DataApplicationRepository
import com.paymong.domain.app.AppLandingViewModelFactory
import com.paymong.domain.app.AppLandinglViewModel
import com.paymong.ui.app.AppMain
import com.paymong.ui.theme.PaymongTheme


class AppMainActivity : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {

    companion object {
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
    }

    private lateinit var capabilityClient: CapabilityClient
    private lateinit var nodeClient: NodeClient
    private lateinit var remoteActivityHelper: RemoteActivityHelper
    private lateinit var messageClient: MessageClient

    private lateinit var gamesSignInClient : GamesSignInClient
    private lateinit var playersClient: PlayersClient

    private lateinit var appLandingViewModelFactory : AppLandingViewModelFactory
    private lateinit var appLandinglViewModel : AppLandinglViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 워치가 연결되어 있을 때 앱 설치 여부 확인 ############################
        capabilityClient = Wearable.getCapabilityClient(this)
        nodeClient = Wearable.getNodeClient(this)
        remoteActivityHelper = RemoteActivityHelper(this)
        messageClient = Wearable.getMessageClient(this)

        // google-play-games #############################################
        PlayGamesSdk.initialize(this)
        gamesSignInClient = PlayGames.getGamesSignInClient(this)
        playersClient = PlayGames.getPlayersClient(this)

        appLandingViewModelFactory = AppLandingViewModelFactory(capabilityClient, nodeClient, remoteActivityHelper, messageClient, gamesSignInClient, playersClient, this.application)
        appLandinglViewModel = ViewModelProvider(this@AppMainActivity, appLandingViewModelFactory)[AppLandinglViewModel::class.java]

//        val dataApplicationRepository = DataApplicationRepository()
//        dataApplicationRepository.setValue("watchId", "abcde")

        // 권한 확인 #######################################################
        if (!isNotificationPermissionGranted()){
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        setContent {
            PaymongTheme {
                AppMain(appLandinglViewModel)
            }
        }
    }
    // 워치가 연결되어 있을 때 앱 설치 여부 확인 ############################
    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        appLandinglViewModel.wearNodesWithApp = capabilityInfo.nodes
        appLandinglViewModel.installCheck()
    }
    override fun onPause() {
        super.onPause()
        capabilityClient.removeListener(this, CAPABILITY_WEAR_APP)
    }
    override fun onResume() {
        super.onResume()
        capabilityClient.addListener(this, CAPABILITY_WEAR_APP)
    }
    // 권한 확인 #######################################################
    private fun isNotificationPermissionGranted(): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            return notificationManager.isNotificationListenerAccessGranted(ComponentName(application, NotificationListener::class.java))
        }
        else {
            return NotificationManagerCompat.getEnabledListenerPackages(applicationContext).contains(applicationContext.packageName)
        }
    }
}
