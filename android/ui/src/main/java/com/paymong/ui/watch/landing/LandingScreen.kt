package com.paymong.ui.watch.landing

import android.graphics.Paint.Align
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.common.R
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.watch.feed.BigWatch
import com.paymong.ui.watch.feed.SmallWatch
import com.paymong.ui.watch.main.MainInfoUI

@Composable
fun Landing(navController: NavController){
    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)


    Logo()

    //2초 후 메인화면으로 전환
    Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate(WatchNavItem.Main.route)
    },2000)
}

@Composable
fun Logo(modifier: Modifier = Modifier){
    val logo = painterResource(R.drawable.watch_logo)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp



    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        if (screenWidthDp < 200) {
            Image(painter = logo, contentDescription = null, modifier = modifier.size(120.dp))
        }
        else {
            Image(painter = logo, contentDescription = null, modifier = modifier.size(150.dp))
        }

    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun LandingPreview(){
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        Landing(navController)
    }
}