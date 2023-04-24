package com.paymong.watch

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import com.paymong.ui.theme.PaymongTheme

class MainActivity : ComponentActivity() {

    private val PERMISSION_CHECK = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearPaymongMain()
        }

        // 신체 활동 접근 권한 확인
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            // 신체 활동 접근 권한이 없으면 권한 요청
            var permissions = arrayOf( Manifest.permission.ACTIVITY_RECOGNITION )
            // 첫 번째 권한 요청에서 거부하면 설정에 직접가서 권한 설정 해야됨
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CHECK)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                    applicationContext,
                    "신체 활동 권한이 없습니다.\n신체 활동 권한에 동의해주세요.\n(애플리케이션 - 권한 - 페이몽 - 신체 활동 허용)",
                    Toast.LENGTH_LONG
                ).show()
                
                // 설정창으로 이동
                startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                finish()
            }
        }
    }
}

@Composable
fun WearPaymongMain() {
    PaymongTheme() {
        NavGraph()
    }
}