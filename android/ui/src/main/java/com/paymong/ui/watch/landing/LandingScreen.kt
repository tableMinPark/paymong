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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.ui.R
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.watch.main.MainInfoUI

@Composable
fun Landing(navController: NavController){
    val img = painterResource(R.drawable.main_bg)
    Box{
        Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
        Logo()

        //3초 후 메인화면으로 전환
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(WatchNavItem.Main.route)
        },2000)
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier){
    val logo = painterResource(R.drawable.watch_logo)
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = logo, contentDescription = null)
    }
}

@Preview(showSystemUi = true)
@Composable
fun LandingPreview(){
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        Landing(navController)
    }
}