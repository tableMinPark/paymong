package com.paymong.ui.app.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.landing.LandingViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.common.R
import com.paymong.ui.app.component.BgGif
import kotlinx.coroutines.delay

@Composable
fun Landing(navController: NavController) {
    val viewModel: LandingViewModel = viewModel()
    LandingUI(navController, viewModel)
}

@Composable
fun LandingUI(
    navController: NavController,
    viewModel: LandingViewModel
) {
    LaunchedEffect(key1 = true){
        delay(2000)
        if(viewModel.id != "") {
            navController.navigate(AppNavItem.Main.route){
                // 백스택 비우기
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                // 스택 첫 화면 메인화면으로 변경
                navController.graph.setStartDestination(AppNavItem.Main.route)
                launchSingleTop =true
            }
        } else{
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
        Landing(navController)
    }
}