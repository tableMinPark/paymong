package com.paymong.ui.watch.battle

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.MessageType
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.landing.MainBackgroundGif

@Composable
fun BattleSelect(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {


    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = 0


    if (screenWidthDp < 200) {
        fontSize = 15
    }
    else {
        fontSize = 20
    }


    if (battleViewModel.matchingState == MatchingCode.SELECT_AFTER){
        navController.navigate(WatchNavItem.BattleActive.route) {
            popUpTo(0)
            launchSingleTop =true
        }
    } else if (battleViewModel.matchingState == MatchingCode.END){
        navController.navigate(WatchNavItem.BattleEnd.route) {
            popUpTo(0)
            launchSingleTop =true
        }
        battleViewModel.battleEnd()
    }

    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.49f)
        ) {
            Button(
                onClick = { battleViewModel.select(MessageType.LEFT)
//                    selectState.value = "LEFT"
//                    navController.navigate(WatchNavItem.BattleActive.route){
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop =true
//                    }
//                    viewModel.isSelectEnd = true
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "왼쪽",  fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                        fontSize = fontSize.sp,
                    modifier = Modifier.fillMaxWidth())
            }
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .width(5.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(1f)
        ) {
            Button(
                onClick = { battleViewModel.select(MessageType.RIGHT)
//                    selectState.value = "RIGHT"
//                    navController.navigate(WatchNavItem.BattleActive.route){
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop =true
//                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "오른쪽",   fontFamily = dalmoori,  fontSize = fontSize.sp,textAlign = TextAlign.Center
                   , modifier = Modifier.fillMaxWidth(1f))
            }
        }
    }


    val animatedProgress by animateFloatAsState(
        targetValue = battleViewModel.battleSelectTime.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    CircularProgressIndicator(
        progress = animatedProgress,
        modifier = Modifier.fillMaxSize(),
        startAngle = 271f,
        endAngle = 270f,
        strokeWidth = 4.dp
    )
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleSelectPreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleSelect(navController, viewModel)
    }
}