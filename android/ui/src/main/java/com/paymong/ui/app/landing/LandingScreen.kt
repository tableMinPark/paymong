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
import com.paymong.domain.app.AppViewModel
import com.paymong.domain.app.AppInstallViewModel
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.delay


@Composable
fun Landing(
    navController: NavController,
    appViewModel: AppViewModel,
    appInstallViewModel : AppInstallViewModel
) {
    LaunchedEffect(key1 = true){
        appViewModel.loginCheck()
        appInstallViewModel.installCheck()
        delay(2000)

        if(appViewModel.loginState == LandingCode.LOGIN_SUCCESS) {
            appViewModel.loginState = LandingCode.LOGIN
            navController.navigate(AppNavItem.Main.route){
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                // 스택 첫 화면 메인화면으로 변경
                navController.graph.setStartDestination(AppNavItem.Main.route)
                launchSingleTop =true
            }
        } else if (appViewModel.loginState == LandingCode.LOGIN_FAIL){
            appViewModel.loginState = LandingCode.LOADING
            navController.navigate(AppNavItem.Login.route)
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LandingPreview() {
    val navController = rememberNavController()
    PaymongTheme {
//        Landing(navController)
    }
}