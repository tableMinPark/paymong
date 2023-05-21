package com.paymong.ui.watch.activity

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.theme.PayMongRed
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background
import kotlinx.coroutines.delay

@Composable
fun TrainingLanding(
    navController: NavHostController,
    watchViewModel : WatchViewModel
) {
    LaunchedEffect(key1 = true){
        delay(1500)
        navController.navigate(WatchNavItem.Training.route){
            popUpTo(WatchNavItem.TrainingLanding.route) {
                inclusive = true
            }
        }
    }
    Background()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val buttonFont = if (screenWidthDp < 200) 20 else 24

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "50번\n연타 시\n훈련 성공!",
            textAlign = TextAlign.Center,
            fontFamily = dalmoori,
            fontSize = buttonFont.sp,
            color = PayMongRed,
            lineHeight = 40.sp
        )
    }
}