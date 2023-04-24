package com.paymong.ui.watch.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.main.MainConditionViewModel
import com.paymong.domain.watch.main.MainInteractionViewModel
import com.paymong.ui.theme.PaymongTheme
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
    Column(
        modifier = Modifier.padding(35.dp)//, bottom = 15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                // nav -> BattleScreen()
                onClick = { navController.navigate(WatchNavItem.Battle.route) }
            ) {
                Text(text = "Battle", textAlign = TextAlign.Center)
            }
            Button(
                // nav -> ActivityScreen()
                onClick = { navController.navigate(WatchNavItem.Activity.route) }
            ) {
                Text(text = "Activity", textAlign = TextAlign.Center)
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                // nav -> FeedScreen()
                onClick = { navController.navigate(WatchNavItem.Feed.route) }
            ) {
                Text(text = "FEED", textAlign = TextAlign.Center)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    animationState.value = AnimationCode.Sleep
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                        coroutineScope.launch {pagerState.animateScrollToPage(1) }
                    }
                }
            ) {
                Text(text = "SLEEP", textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    animationState.value = AnimationCode.Poop
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                        coroutineScope.launch {pagerState.animateScrollToPage(1) }
                    }
                }
            ) {
                Text(text = "POOP", textAlign = TextAlign.Center)
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