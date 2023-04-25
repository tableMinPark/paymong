package com.paymong.ui.app.info_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.domain.app.info_detail.InfoDetailViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun InfoDetail(navController: NavController) {
    val viewModel: InfoDetailViewModel = viewModel()
    InfoDetailUI(navController, viewModel)
}

@Composable
fun InfoDetailUI(
    navController: NavController,
    viewModel: InfoDetailViewModel
    ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "캐릭터 정보 페이지", textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoDetailPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        InfoDetail(navController)
    }
}