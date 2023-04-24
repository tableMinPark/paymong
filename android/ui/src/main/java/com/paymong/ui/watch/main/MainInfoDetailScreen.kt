package com.paymong.ui.watch.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.paymong.domain.watch.main.MainInfoDetailViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun MainInfoDetail() {
    val viewModel: MainInfoDetailViewModel = viewModel()
    MainInfoDetailUI(viewModel)
}

@Composable
fun MainInfoDetailUI(
    viewModel: MainInfoDetailViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = viewModel.name, textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = viewModel.age, textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%dkg", viewModel.weight), textAlign = TextAlign.Center)
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainInfoDetailPreview() {
    PaymongTheme {
        MainInfoDetail()
    }
}