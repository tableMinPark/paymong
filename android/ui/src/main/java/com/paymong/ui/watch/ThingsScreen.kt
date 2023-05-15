package com.paymong.ui.watch


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.common.R
import com.paymong.common.code.MapCode
import com.paymong.domain.entity.Things
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.MainBackgroundGif

@Composable
fun Things() {
    // 배경
    Background(false)

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Image(painterResource(R.drawable.heart), contentDescription = null)
    }
}