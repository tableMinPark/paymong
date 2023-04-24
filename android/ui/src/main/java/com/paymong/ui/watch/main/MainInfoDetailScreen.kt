package com.paymong.ui.watch.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.paymong.domain.watch.main.MainInfoDetailViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

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
            Text(text = viewModel.name,
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = 24.sp)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = viewModel.age,
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%dkg", viewModel.weight),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = 20.sp)
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