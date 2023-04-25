package com.paymong.ui.app.help

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
import com.paymong.domain.app.help.HelpViewModel
import com.paymong.ui.theme.PaymongTheme


@Composable
fun Help(navController: NavController) {
    val viewModel: HelpViewModel = viewModel()
    HelpUI(navController, viewModel)
}

@Composable
fun HelpUI(
    navController: NavController,
    viewModel: HelpViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "도움말 페이지", textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Help(navController)
    }
}