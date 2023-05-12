package com.paymong

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.paymong.common.code.ToastMessage
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.wearable.*
import com.paymong.data.repository.DataApplicationRepository
import com.paymong.domain.watch.WatchLandingViewModel
import com.paymong.domain.watch.WatchLandingViewModelFactory
import com.paymong.ui.watch.WatchMain

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 설치여부확인
        capabilityClient = Wearable.getCapabilityClient(this)
        remoteActivityHelper = RemoteActivityHelper(this)
        messageClient = Wearable.getMessageClient(this)

        watchLandingViewModelFactory = WatchLandingViewModelFactory(capabilityClient, remoteActivityHelper, messageClient, this.application)
        watchLandingViewModel = ViewModelProvider(this@WatchMainActivity, watchLandingViewModelFactory)[WatchLandingViewModel::class.java]

        // 필수 권한 확인
        checkPermission()

        // 화면 켜짐 유지
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            WatchMain(watchLandingViewModel)
        }

    }
    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        watchLandingViewModel.androidPhoneNodeWithApp = capabilityInfo.nodes.firstOrNull()
        watchLandingViewModel.installCheck()
    }
    override fun onPause() {
        super.onPause()
        capabilityClient.removeListener(this, CAPABILITY_PHONE_APP)
    }
    override fun onResume() {
        super.onResume()
        capabilityClient.addListener(this, CAPABILITY_PHONE_APP)
    }
    // 필수 권한 확인
    private fun checkPermission() {
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
            var permissions = arrayOf(
                Manifest.permission.ACTIVITY_RECOGNITION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            // 첫 번째 권한 요청에서 거부하면 설정에 직접가서 권한 설정 해야됨
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CHECK)
        }
    }
    // 권한 설정
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

