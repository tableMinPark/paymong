package com.paymong.ui.watch.common

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.BackgroundCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.ToastMessage

@Composable
fun Background(
    mapCode: MapCode
) {
    val background = painterResource(mapCode.code)
    Image(painter = background, contentDescription = null, contentScale = ContentScale.Crop)
}

@Composable
fun Background(
    bgCode: BackgroundCode
) {
    val background = painterResource(bgCode.code)
    Image(painter = background, contentDescription = null, contentScale = ContentScale.Crop)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Loading() {
    Background(MapCode.MP000)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val loadBarSize = if (screenWidthDp < 200) 45 else 55
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

@Composable
fun Logo(){
    val logo = painterResource(R.drawable.watch_logo)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    if (screenWidthDp < 200) {
        Image(painter = logo, contentDescription = null, modifier = Modifier.size(120.dp))
    }
    else {
        Image(painter = logo, contentDescription = null, modifier = Modifier.size(150.dp))
    }
}

fun showToast(context : Context, toastMessage: ToastMessage) {
    Toast.makeText(
        context,
        toastMessage.message,
        Toast.LENGTH_SHORT
    ).show()
}
