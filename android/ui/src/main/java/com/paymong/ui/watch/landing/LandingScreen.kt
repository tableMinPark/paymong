package com.paymong.ui.watch.landing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.code.LandingCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.SocketCode
import com.paymong.domain.watch.WatchLandingViewModel
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.Logo
import kotlinx.coroutines.delay

@Composable
fun Landing(
    navController: NavController,
    watchLandingViewModel : WatchLandingViewModel
){
    LaunchedEffect(key1 = true){
        delay(1000)
        watchLandingViewModel.loginCheck()
    }
    Background(MapCode.MP000, false)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val fontSize = if (screenWidthDp < 200) 12 else 15

    // 로그인  (리프레시 있는 경우)
    when(watchLandingViewModel.loginState) {
         LandingCode.LOGIN_SUCCESS -> {
             watchLandingViewModel.loginState = LandingCode.DONE
                     navController.navigate(WatchNavItem.Main.route){
                         popUpTo(navController.graph.id) {
                     inclusive = true
                 }
                 // 스택 첫 화면 메인화면으로 변경
                 navController.graph.setStartDestination(WatchNavItem.Main.route)
                 launchSingleTop =true
             }
        }
        // 로그인 실패 (리프레시 없음)
        LandingCode.LOGIN_FAIL -> {
            watchLandingViewModel.installCheck()
        }
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (watchLandingViewModel.landingCode == LandingCode.NOT_INSTALL) {
                    watchLandingViewModel.openAppInStoreOnPhone()
                } else if (watchLandingViewModel.landingCode == LandingCode.INSTALL) {
                    watchLandingViewModel.landingCode = LandingCode.LOADING
                    watchLandingViewModel.openAppOnPhone()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (watchLandingViewModel.landingCode) {
            LandingCode.NOT_INSTALL -> {
                Text(
                    text = "모바일 앱 설치 후\n설정이 필요합니다.\n\n터치해서\n\n모바일 앱 설치하기",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = dalmoori,
                    color = PayMongRed200,
                    fontSize = fontSize.sp,
                )
            }
            LandingCode.INSTALL -> {
                Text(
                    text = "초기 설정이 필요합니다.\n\n터치해서\n\n모바일 앱에서 초기 설정하기",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = dalmoori,
                    color = PayMongRed200,
                    fontSize = fontSize.sp,
                )
            }
            else -> Logo()
        }
    }
}
