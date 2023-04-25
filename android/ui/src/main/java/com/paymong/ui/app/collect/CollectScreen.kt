package com.paymong.ui.app.collect

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
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
import com.paymong.domain.app.collect.CollectViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PaymongTheme


@Composable
fun Collect(navController: NavController) {
    val viewModel: CollectViewModel = viewModel()
    CollectUI(navController, viewModel)
}

@Composable
fun CollectUI(
    navController: NavController,
    viewModel: CollectViewModel
) {
    Scaffold(
        topBar = { TopBar(msg = "컬렉션", navController = navController) }
    ) {
        Box(Modifier.padding(it)){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "컬렉션", textAlign = TextAlign.Center)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate(AppNavItem.CollectDetail.route + "/paymong/${viewModel.memberId}") },
                    modifier = Modifier.size(60.dp)
                ) {
                    Text(text = "Paymong", textAlign = TextAlign.Center)
                }
                Button(
                    onClick = { navController.navigate(AppNavItem.CollectDetail.route + "/map/${viewModel.memberId}") },
                    modifier = Modifier.size(60.dp)
                    ) {
                    Text(text = "Map", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Collect(navController)
    }
}