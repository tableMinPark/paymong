package com.paymong

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.paymong.common.code.ToastMessage
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.wearable.*
import com.paymong.common.code.SocketCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.data.repository.DataApplicationRepository
import com.paymong.domain.watch.WatchLandingViewModel
import com.paymong.domain.watch.WatchLandingViewModelFactory
import com.paymong.domain.watch.WatchViewModel
import com.paymong.domain.watch.WatchViewModelFactory
import com.paymong.ui.watch.WatchMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchMainActivity : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {
    companion object {
        private const val PERMISSION_CHECK = 100
        private const val CAPABILITY_PHONE_APP = "app_paymong"
    }

    private lateinit var capabilityClient: CapabilityClient
    private lateinit var remoteActivityHelper: RemoteActivityHelper
    private lateinit var messageClient: MessageClient
    private lateinit var watchLandingViewModelFactory : WatchLandingViewModelFactory
    private lateinit var watchLandingViewModel : WatchLandingViewModel

    private lateinit var watchViewModelFactory: WatchViewModelFactory
    private lateinit var watchViewModel: WatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 필수 권한 확인
        isNotificationPermissionGranted()

        DataApplicationRepository().setValue("playerId", "")

        // 설치 여부 확인
        capabilityClient = Wearable.getCapabilityClient(this)
        remoteActivityHelper = RemoteActivityHelper(this)
        messageClient = Wearable.getMessageClient(this)
        watchLandingViewModelFactory = WatchLandingViewModelFactory(capabilityClient, remoteActivityHelper, messageClient, this.application)
        watchLandingViewModel = ViewModelProvider(this@WatchMainActivity, watchLandingViewModelFactory)[WatchLandingViewModel::class.java]

        watchViewModelFactory = WatchViewModelFactory(this.application)
        watchViewModel = ViewModelProvider(this@WatchMainActivity, watchViewModelFactory)[WatchViewModel::class.java]

//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            WatchMain(watchLandingViewModel)
        }
    }
    override fun onPause() {
        super.onPause()
        try {
            watchViewModel.isSocketConnect = SocketCode.LOADING
            capabilityClient.removeListener(this, CAPABILITY_PHONE_APP)
            // 한번이라도 소켓에 연결되었다면 연결된 소켓 끊음
            if (watchViewModel.isInitialized()) {
                watchViewModel.disConnectSocket()
                DataApplicationRepository().setValue("isPause", "true")
            }
            Log.d("start", "onPause : " + DataApplicationRepository().getValue("isPause"))
        } catch (_: Exception) {}
    }

    override fun onDestroy() {
        DataApplicationRepository().setValue("isPause", "false")
        Log.d("start", "onStop : " + DataApplicationRepository().getValue("isPause"))
        super.onDestroy()
    }

    override fun onResume() {
        watchViewModel.isSocketConnect = SocketCode.LOADING
        super.onResume()
        try {
            Log.d("start", "onResume : " + DataApplicationRepository().getValue("isPause"))
            capabilityClient.addListener(this, CAPABILITY_PHONE_APP)
            val dataApplicationRepository = DataApplicationRepository()
            val accessToken = dataApplicationRepository.getValue("accessToken")
            CoroutineScope(Dispatchers.Main).launch {
                if (accessToken != "") {
                    // 엑세스 토큰이 있어서 바로 소켓 연결 가능한 경우 (로그인을 이미 한 경우)
                    watchViewModel.connectSocket()
                    // 일시 정지 경우
                    val isPause = DataApplicationRepository().getValue("isPause")
                    Log.d("start", "isPause: $isPause, ${watchViewModel.isSocketConnect}")
                    if (isPause == "true") {
                        watchViewModel.isSocketConnect = SocketCode.CONNECT
                        watchLandingViewModel.startDestination = WatchNavItem.Main.route
                    }

                } else
                // 엑세스 토큰이 없어서 소켓 연결이 불가능한 경우 (로그인이 필요한 경우) -> 로그인 이후 메인 화면 로딩 시 소켓 연결
                    watchViewModel.isSocketConnect = SocketCode.NOT_TOKEN
            }
        } catch (_: Exception) {}
    }
    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        watchLandingViewModel.androidPhoneNodeWithApp = capabilityInfo.nodes.firstOrNull()
        watchLandingViewModel.installCheck()
    }
    // 필수 권한 확인
    private fun isNotificationPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_DENIED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // 신체 활동 접근 권한이 없으면 권한 요청
            val permissions = arrayOf(
                Manifest.permission.ACTIVITY_RECOGNITION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            // 첫 번째 권한 요청에서 거부하면 설정에 직접가서 권한 설정 해야됨
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CHECK)
        }
    }
    // 권한 설정
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                    applicationContext,
                    ToastMessage.PERMISSION_DENIED.message,
                    Toast.LENGTH_LONG
                ).show()
                
                // 설정창으로 이동
                startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                finish()
            }
        }
    }
}

