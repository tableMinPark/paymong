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

        if (appLandinglViewModel.landingCode == LandingCode.LOGIN_SUCCESS) {
            appLandinglViewModel.landingCode = LandingCode.LOADING
            navController.navigate(AppNavItem.Main.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                navController.graph.setStartDestination(AppNavItem.Main.route)
                launchSingleTop = true
            }
        }
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
        // 웨어러블이 등록되어 있는 경우
        if (appLandinglViewModel.landingCode == LandingCode.REGIST_WEARABLE_SUCCESS) {
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
        // 웨어러블 기기에 앱이 설치되어 있지 않은 경우
        if (appLandinglViewModel.landingCode == LandingCode.HAS_WEARABLE_FAIL) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val google = painterResource(R.drawable.google_login)

                Image(painter = google, contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // 애니메이션 제거
                        onClick = { appLandinglViewModel.openPlayStoreOnWearDevicesWithoutApp() }
                    )
                )
            }
        }
        // 웨어러블 기기에 앱이 설치되어 있는 경우
        if (appLandinglViewModel.landingCode == LandingCode.HAS_WEARABLE_SUCCESS) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val google = painterResource(R.drawable.google_login)

                Image(painter = google, contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // 애니메이션 제거
                        onClick = { appLandinglViewModel.registWearable() }
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