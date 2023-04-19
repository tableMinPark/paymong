package com.paymong.ui.watch.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainInteraction(
    animationState: MutableState<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Button(
                // nav -> FeedScreen()
                onClick = { navController.navigate(WatchNavItem.Feed.route) },
                modifier = Modifier.size(60.dp)
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
                    animationState.value = "poop"
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                        coroutineScope.launch {pagerState.animateScrollToPage(1) }
                    }
                },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "POOP", textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    animationState.value = "sleep"
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                        coroutineScope.launch {pagerState.animateScrollToPage(1) }
                    }
                },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "SLEEP", textAlign = TextAlign.Center)
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Button(
                // nav -> ActivityScreen()
                onClick = { navController.navigate(WatchNavItem.Activity.route) },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "Activity", textAlign = TextAlign.Center)
            }
            Button(
                // nav -> BattleScreen()
                onClick = { navController.navigate(WatchNavItem.Battle.route) },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "Battle", textAlign = TextAlign.Center)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainInteractionPreview() {
    val animationState = remember { mutableStateOf("") }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        MainInteraction(animationState, pagerState, coroutineScope, navController)
    }
}