package com.paymong.ui.app.collect

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.domain.app.collect.CollectDetailViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PaymongTheme

@Composable
fun CollectDetail(navController: NavController) {
    val viewModel: CollectDetailViewModel = viewModel()
    CollectDetailUI(navController, viewModel)
}

@Composable
fun CollectDetailUI(
    navController: NavController,
    viewModel: CollectDetailViewModel
) {
    Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center
) {
        Scaffold(
            topBar = { TopBar(msg = viewModel.collectCategory, navController = navController) }
        ) {
            Box(Modifier.padding(it)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "본문", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndingDetailPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        CollectDetail(navController)
    }
}