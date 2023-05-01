package com.paymong.ui.watch.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.main.MainConditionViewModel
import com.paymong.domain.watch.main.MainInteractionViewModel
import com.paymong.common.R
import com.paymong.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainInteraction(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    val viewModel: MainInteractionViewModel = viewModel()
    MainInteractionUI(animationState, pagerState, coroutineScope, navController, viewModel)

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainInteractionUI(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    viewModel: MainInteractionViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var buttonSize = 0
    var buttonIconSize = 0

    if (screenWidthDp < 200) {
        buttonSize = 45
        buttonIconSize = 25
    }
    else {
        buttonSize = 55
        buttonIconSize = 35
    }


    Column(
        modifier = Modifier.padding(15.dp)//, bottom = 15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = PayMongRed.copy(alpha = 0.8f)),
                border = ButtonDefaults.outlinedButtonBorder(PayMongRed),

                modifier = Modifier.height(buttonSize.dp).width(buttonSize.dp),
                // nav -> BattleScreen()
                onClick = { navController.navigate(WatchNavItem.Battle.route) }
            ) {
//                Text(text = "Battle", textAlign = TextAlign.Center)
                val battle = painterResource(R.drawable.battle)
                Image(painter = battle, contentDescription = null, modifier = Modifier.size(buttonIconSize.dp))
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = PayMongGreen.copy(alpha = 0.8f)),
                border = ButtonDefaults.outlinedButtonBorder(PayMongGreen),
                modifier = Modifier.height(buttonSize.dp).width(buttonSize.dp),
                // nav -> ActivityScreen()
                onClick = { navController.navigate(WatchNavItem.Activity.route) }
            ) {
//                Text(text = "Activity", textAlign = TextAlign.Center)
                val activity = painterResource(R.drawable.activity)
                Image(painter = activity, contentDescription = null, modifier = Modifier.size(buttonIconSize.dp))
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = PayMongYellow.copy(alpha = 0.8f)),
                border = ButtonDefaults.outlinedButtonBorder(PayMongYellow),
                modifier = Modifier.height(buttonSize.dp).width(buttonSize.dp),
                // nav -> FeedScreen()
                onClick = { navController.navigate(WatchNavItem.Feed.route) }
            ) {
//                Text(text = "FEED", textAlign = TextAlign.Center)
                val feed = painterResource(R.drawable.feed)
                Image(painter = feed, contentDescription = null, modifier = Modifier.size(buttonIconSize.dp))
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = PayMongBlue.copy(alpha = 0.8f)),
                border = ButtonDefaults.outlinedButtonBorder(PayMongBlue),
                modifier = Modifier.height(buttonSize.dp).width(buttonSize.dp),
                onClick = {
                    animationState.value = AnimationCode.Sleep
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                        coroutineScope.launch {pagerState.animateScrollToPage(1) }
                    }
                }
            ) {
//                Text(text = "SLEEP", textAlign = TextAlign.Center)
                val sleep = painterResource(R.drawable.sleep)
                Image(painter = sleep, contentDescription = null, modifier = Modifier.size(buttonIconSize.dp))
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = PayMongPurple.copy(alpha = 0.8f)),
                border = ButtonDefaults.outlinedButtonBorder(PayMongPurple),
                modifier = Modifier.height(buttonSize.dp).width(buttonSize.dp),
                onClick = {
                    animationState.value = AnimationCode.Poop
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                        coroutineScope.launch {pagerState.animateScrollToPage(1) }
                    }
                }
            ) {
//                Text(text = "POOP", textAlign = TextAlign.Center)
                val poop = painterResource(R.drawable.poop)
                Image(painter = poop, contentDescription = null, modifier = Modifier.size(buttonIconSize.dp))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainInteractionPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        MainInteraction(animationState, pagerState, coroutineScope, navController)
    }
}