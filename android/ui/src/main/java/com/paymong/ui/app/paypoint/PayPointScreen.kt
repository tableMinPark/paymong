package com.paymong.ui.app.paypoint

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.domain.app.paypoint.PayPointViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun PayPoint(navController: NavController) {
    val viewModel: PayPointViewModel = viewModel()
    PayPointUI(navController, viewModel)
}

@Composable
fun PayPointUI(
    navController: NavController,
    viewModel: PayPointViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "페이 포인트 페이지", textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PayPointPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        PayPoint(navController)
    }
}