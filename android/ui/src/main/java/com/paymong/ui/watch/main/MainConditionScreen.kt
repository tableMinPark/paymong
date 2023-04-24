package com.paymong.ui.watch.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.paymong.domain.watch.main.MainConditionViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun MainCondition() {
    val viewModel: MainConditionViewModel = viewModel()
    MainConditionUI(viewModel)
}

@Composable
fun MainConditionUI(
    viewModel: MainConditionViewModel
) {
    Column(
            verticalArrangement = Arrangement.SpaceAround,
    modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%f", viewModel.health), textAlign = TextAlign.Center)
            Text(text = String.format("%f", viewModel.satiety), textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%f", viewModel.strength), textAlign = TextAlign.Center)
            Text(text = String.format("%f", viewModel.sleep), textAlign = TextAlign.Center)
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainConditionPreview() {
    PaymongTheme {
        MainCondition()
    }
}