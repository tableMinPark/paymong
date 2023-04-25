package com.paymong.ui.app.landing

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.landing.LandingViewModel
import com.paymong.ui.theme.PaymongTheme

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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "랜딩 패이지", textAlign = TextAlign.Center)
            Button(
                onClick = { navController.navigate(AppNavItem.Login.route) },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "다음페이지", textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Landing(navController)
    }
}