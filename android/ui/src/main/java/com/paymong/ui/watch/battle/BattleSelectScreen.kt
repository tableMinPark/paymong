package com.paymong.ui.watch.battle

import android.util.Half.toFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.R
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.BattleSelectViewModel
import com.paymong.domain.watch.battle.BattleActiveViewModel
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BattleSelect(
    navController: NavHostController
) {
    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)

    val viewModel : BattleSelectViewModel = viewModel()
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Button(
                onClick = {
//                    selectState.value = "LEFT"
                    navController.navigate(WatchNavItem.BattleActive.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                    }
//                    viewModel.isSelectEnd = true
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "왼쪽", modifier = Modifier.padding(end = 40.dp))
            }
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .width(5.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Button(
                onClick = {
//                    selectState.value = "RIGHT"
                    navController.navigate(WatchNavItem.BattleActive.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "오른쪽", modifier = Modifier.padding(start = 30.dp))
            }
        }
    }
    var time = viewModel.second.toFloat()

    val animatedProgress by animateFloatAsState(
        targetValue = time,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    CircularProgressIndicator(
        progress = animatedProgress,
        modifier = Modifier.fillMaxSize(),
        startAngle = 271f,
        endAngle = 270f,
        strokeWidth = 4.dp
    )
    viewModel.navigateToActive {
//        navController.navigate(WatchNavItem.BattleActive.route)
        navController.navigate(WatchNavItem.BattleEnd.route)
    }

}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleSelectPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleSelect(navController)
    }
}