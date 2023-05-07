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
import com.google.android.gms.games.PlayGamesSdk
import com.google.android.gms.wearable.*
import com.paymong.domain.app.AppInstallViewModel
import com.paymong.domain.app.AppInstallViewModelFactory
import com.paymong.ui.app.AppMain
import com.paymong.ui.theme.PaymongTheme

class AppMainActivity : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {

    companion object {
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
    }

    private lateinit var capabilityClient: CapabilityClient
    private lateinit var nodeClient: NodeClient
    private lateinit var remoteActivityHelper: RemoteActivityHelper

    private lateinit var appInstallViewModelFactory : AppInstallViewModelFactory
    private lateinit var appInstallViewModel : AppInstallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 워치가 연결되어 있을 때 앱 설치 여부 확인 ############################
        capabilityClient = Wearable.getCapabilityClient(this)
        nodeClient = Wearable.getNodeClient(this)
        remoteActivityHelper = RemoteActivityHelper(this)
        appInstallViewModelFactory = AppInstallViewModelFactory(capabilityClient, nodeClient, remoteActivityHelper, this.application)
        appInstallViewModel = ViewModelProvider(this@AppMainActivity, appInstallViewModelFactory)[AppInstallViewModel::class.java]
        // google-play-games #############################################
        PlayGamesSdk.initialize(this)
        // 권한 확인 #######################################################
        if (!isNotificationPermissionGranted()){
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        setContent {
            PaymongTheme {
                AppMain(appInstallViewModel)
            }
        }
    }
    // 워치가 연결되어 있을 때 앱 설치 여부 확인 ############################
    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        Log.e("mainActivity", "change")
        appInstallViewModel.wearNodesWithApp = capabilityInfo.nodes
        appInstallViewModel.installCheck()
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
