package com.paymong.ui.app.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.R
import com.paymong.common.code.LandingCode
import com.paymong.common.code.ToastMessage
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.AppLandinglViewModel
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.theme.PaymongTheme

@Composable
fun Login(
    navController: NavController,
    appLandinglViewModel : AppLandinglViewModel
) {
    LaunchedEffect(key1 = true) {
        // 웨어러블 최초 등록 여부 확인
        appLandinglViewModel.registCheck()
    }
    if (appLandinglViewModel.landingCode == LandingCode.LOGIN_SUCCESS) {
        appLandinglViewModel.landingCode = LandingCode.LOGIN
        Log.d("Login()", "LOGIN_SUCCESS")
        navController.navigate(AppNavItem.Main.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            navController.graph.setStartDestination(AppNavItem.Main.route)
            launchSingleTop = true
        }
    } 
    // 등록된 와치가 있는 경우
    else if (appLandinglViewModel.landingCode == LandingCode.REGIST_WEARABLE_SUCCESS) {
        Log.d("Login()", "REGIST_WEARABLE_SUCCESS")
//        Toast.makeText(
//            getApplication(),
//            ToastMessage.WEARABLE_INSTALL_SUCCESS.message,
//            Toast.LENGTH_SHORT
//        ).show()
    }
    // 연결된 기기가 있고 설치된 경우
    else if (appLandinglViewModel.landingCode == LandingCode.HAS_WEARABLE_SUCCESS) {
        Log.d("Login()", "HAS_WEARABLE_SUCCESS")
//        Toast.makeText(
//            getApplication(),
//            ToastMessage.WEARABLE_INSTALL_SUCCESS.message,
//            Toast.LENGTH_SHORT
//        ).show()

    
    }
    // 연결된 웨어러블 기기에 앱이 설치되지 않은 경우
    else if (appLandinglViewModel.landingCode == LandingCode.HAS_WEARABLE_FAIL) {
        Log.d("Login()", "HAS_WEARABLE_FAIL")
//        Toast.makeText(
//            getApplication(),
//            ToastMessage.WEARABLE_INSTALL_SUCCESS.message,
//            Toast.LENGTH_SHORT
//        ).show()

        // 와치에 설치할 수 있도록 설치 페이지 띄울 수 있는 요청 보냄
        appLandinglViewModel.openPlayStoreOnWearDevicesWithoutApp()
    } else if (appLandinglViewModel.landingCode == LandingCode.LOGIN_FAIL) {
        Log.d("Login()", "LOGIN_FAIL")
//        Toast.makeText(
//            getApplication(),
//            ToastMessage.WEARABLE_INSTALL_SUCCESS.message,
//            Toast.LENGTH_SHORT
//        ).show()
    }


    BgGif()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val logo = painterResource(R.drawable.app_logo)
            Image(painter = logo, contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .padding(80.dp))
        }

        // 웨어러블이 등록되어 있는 경우 or 로그인 실패한 경우 (구글 로그인 버튼)
        if (appLandinglViewModel.landingCode == LandingCode.REGIST_WEARABLE_SUCCESS ||
            appLandinglViewModel.landingCode == LandingCode.LOGIN_FAIL) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                val google = painterResource(R.drawable.google_login)

                Image(painter = google, contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // 애니메이션 제거
                        onClick = { appLandinglViewModel.googlePlayLogin() }
                    )
                )
            }
        }
        // 웨어러블이 등록되지 않은 경우 (웨어러블 기기 등록 버튼 필요 + 최초 등록 안됨)
        if (appLandinglViewModel.landingCode == LandingCode.HAS_WEARABLE_SUCCESS ||
            appLandinglViewModel.landingCode == LandingCode.HAS_WEARABLE_FAIL) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val google = painterResource(R.drawable.google_login)

                Image(painter = google, contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // 애니메이션 제거
                        onClick = { appLandinglViewModel.googlePlayRegist() }
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LandingLoginPreview() {
    val navController = rememberNavController()
    PaymongTheme {
    }
}