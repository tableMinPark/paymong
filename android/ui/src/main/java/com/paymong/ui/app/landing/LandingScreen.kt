package com.paymong.ui.app.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.R
import com.paymong.common.code.LandingCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.AppLandinglViewModel
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.delay


@Composable
fun Landing(
    navController: NavController,
    appLandingViewModel : AppLandinglViewModel
) {
    LaunchedEffect(key1 = true){
        appLandingViewModel.refreshLogin()
        delay(2000)
    }
    BgGif()

    // 리프레시 로그인 성공
    if(appLandingViewModel.landingCode == LandingCode.LOGIN_SUCCESS) {
        appLandingViewModel.landingCode = LandingCode.DONE
        navController.navigate(AppNavItem.Main.route){
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            // 스택 첫 화면 메인화면으로 변경
            navController.graph.setStartDestination(AppNavItem.Main.route)
            launchSingleTop =true
        }
    }
    // 리프레시 로그인 실패
    else if (appLandingViewModel.landingCode == LandingCode.LOGIN_FAIL){
        appLandingViewModel.landingCode = LandingCode.LOADING
        navController.navigate(AppNavItem.Login.route){
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }


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
    }
}