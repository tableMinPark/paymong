package com.paymong.watch

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
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import com.paymong.common.code.ToastMessage
import com.paymong.ui.watch.WatchMain

class MainActivity : ComponentActivity() {
    companion object {
        private const val PERMISSION_CHECK = 100
        private const val VOICE_TRANSCRIPTION_CAPABILITY_NAME = "voice_transcription"
        private var transcriptionNodeId: String? = null
        private const val VOICE_TRANSCRIPTION_MESSAGE_PATH = "/voice_transcription"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 필수 권한 확인
        checkPermission()

        // 화면 켜짐 유지
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            WatchMain()
        }
    }

    // 필수 권한 확인
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
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

