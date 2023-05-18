package com.paymong.ui.app.login

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.LandingCode
import com.paymong.common.code.ToastMessage
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.AppLandinglViewModel
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.dalmoori

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Login(
    navController: NavController,
    appLandingViewModel : AppLandinglViewModel
) {
    LaunchedEffect(key1 = true) {
        if (appLandingViewModel.landingCode == LandingCode.LOGIN_FAIL) {
            appLandingViewModel.registCheck()
        }
    }
    Log.e("landing", "${appLandingViewModel.landingCode}")
    when (appLandingViewModel.landingCode) {
        LandingCode.LOGIN_SUCCESS -> {
            appLandingViewModel.landingCode = LandingCode.DONE
            navController.navigate(AppNavItem.Main.route) {
                popUpTo(AppNavItem.Main.route) {
                    inclusive = true
                }
                navController.graph.setStartDestination(AppNavItem.Main.route)
                launchSingleTop = true
            }
        }
        // 돈없어서 워치 없는 친구들 여기야 여기!
        LandingCode.REGIST_WEARABLE_FAIL -> {
            Toast.makeText(
                LocalContext.current,
                ToastMessage.REGIST_WEARABLE_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        LandingCode.CANT_LOGIN -> {
            Toast.makeText(
                LocalContext.current,
                ToastMessage.LOGIN_FAIL.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        else -> {}
    }

    BgGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val fontSize = if (screenWidthDp < 200) 12 else 15

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
        if (appLandingViewModel.landingCode == LandingCode.REGIST_WEARABLE_FAIL) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "등록된 웨어러블 기기가 없고\n\n주변에 등록 가능한 기기가 없습니다.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = dalmoori,
                    color = PayMongRed200,
                    fontSize = fontSize.sp
                )
            }
        }

        // 웨어러블이 등록되어 있는 경우 or 로그인 실패한 경우 (구글 로그인 버튼)
        if (appLandingViewModel.landingCode == LandingCode.REGIST_WEARABLE_SUCCESS ||
            appLandingViewModel.landingCode == LandingCode.LOGIN_FAIL) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                val google = painterResource(R.drawable.google_login)

                Image(painter = google, contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { appLandingViewModel.googlePlayLogin() }
                    )
                )
            }
        }
        if (appLandingViewModel.landingCode == LandingCode.HAS_WEARABLE_FAIL) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { appLandingViewModel.openPlayStoreOnWearDevicesWithoutApp() },
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "등록 된 웨어러블 기기가 없습니다.\n\n터치하여 앱 설치하기",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = dalmoori,
                    color = PayMongRed200,
                    fontSize = fontSize.sp
                )
            }
        }
        // 웨어러블이 등록되지 않은 경우 (웨어러블 기기 등록 버튼 필요 + 최초 등록 안됨)
        if (appLandingViewModel.landingCode == LandingCode.HAS_WEARABLE_SUCCESS) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val google = painterResource(R.drawable.wearable_connect)
                Image(painter = google, contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { appLandingViewModel.googlePlayRegist() }
                    )
                )
            }
        }
    }
}