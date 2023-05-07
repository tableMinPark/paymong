package com.paymong.ui.watch.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.paymong.domain.watch.main.MainInfoDetailViewModel
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun MainInfoDetail() {
    val mainviewModel: MainInfoViewModel = viewModel()
    val infoviewModel: MainInfoDetailViewModel = viewModel()
    MainInfoDetailUI(mainviewModel, infoviewModel)
}

@Composable
fun MainInfoDetailUI(
    mainviewModel: MainInfoViewModel,
    infoviewModel: MainInfoDetailViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var nameFontSize = 0
    var ageFontSize = 0
    var weightFontSize = 0


    if (screenWidthDp < 200) {
        nameFontSize = 20
        ageFontSize=15
        weightFontSize=15

    }
    else {
        nameFontSize = 24
        ageFontSize = 20
        weightFontSize= 20
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text(text = mainviewModel.mong.name,
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = nameFontSize.sp)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = infoviewModel.age,
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = ageFontSize.sp,
                modifier = Modifier.padding(vertical = 16.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%dg", infoviewModel.mongInfo.weight),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = weightFontSize.sp)
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