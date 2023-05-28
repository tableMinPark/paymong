package com.paymong.ui.app.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.common.R
import com.paymong.common.code.BackgroundCode
import com.paymong.common.code.MapCode
import com.paymong.ui.watch.common.LoadingGif

@Composable
fun Background(
    mapCode: MapCode
) {
    val background = painterResource(mapCode.phoneCode)
    Image(
        painter = background, contentDescription = null, contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}

@Composable
fun Background(
    bgCode: BackgroundCode
) {
    val background = painterResource(bgCode.code)
    Image(painter = background, contentDescription = null, contentScale = ContentScale.Crop)
}


@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val logo = painterResource(R.drawable.app_logo)
            Image(
                painter = logo, contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .padding(80.dp)
            )
        }
        LoadingBar()
    }
}

@Composable
fun LoadingBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val loadBarSize = 75
        Box(
            modifier = Modifier
                .width(loadBarSize.dp)
                .height(loadBarSize.dp)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            LoadingGif()
        }
    }
}