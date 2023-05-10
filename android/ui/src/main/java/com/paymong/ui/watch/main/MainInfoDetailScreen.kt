package com.paymong.ui.watch.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.paymong.domain.watch.refac.WatchViewModel
import com.paymong.ui.theme.dalmoori

@Composable
fun MainInfoDetail(
    watchViewModel: WatchViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val nameFontSize = if(screenWidthDp < 200) 20 else 24
    val ageFontSize = if(screenWidthDp < 200) 15 else 20
    val weightFontSize = if(screenWidthDp < 200) 15 else 20

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
            Text(text = watchViewModel.mong.name,
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = nameFontSize.sp)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = watchViewModel.age,
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = ageFontSize.sp,
                modifier = Modifier.padding(vertical = 16.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%dg", watchViewModel.mongInfo.weight),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = weightFontSize.sp)
        }
    }
}