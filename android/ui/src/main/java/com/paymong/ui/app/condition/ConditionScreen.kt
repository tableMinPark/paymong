package com.paymong.ui.app.condition

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.domain.app.condition.ConditionViewModel
import com.paymong.ui.theme.PaymongTheme


@Composable
fun Condition(navController: NavController) {
    val viewModel: ConditionViewModel = viewModel()
    ConditionUI(navController, viewModel)
}

@Composable
fun ConditionUI(
    navController: NavController,
    viewModel: ConditionViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "캐릭터 컨디션 페이지", textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConditionPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Condition(navController)
    }
}