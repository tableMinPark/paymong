package com.paymong.ui.watch.feed

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.feed.FeedBuyListViewModel
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedBuyList(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    val viewModel: FeedBuyListViewModel = viewModel()
    FeedBuyListUI(animationState, pagerState, coroutineScope, navController, viewModel)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedBuyListUI(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    viewModel: FeedBuyListViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%d", viewModel.payPoint), textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = viewModel.name, textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = viewModel.foodCode, textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("+ %d", viewModel.price), textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.prevButtonClick() },
            ) {
                Text(text = "이전")
            }
            Button(
                onClick = { viewModel.nextButtonClick() },
            ) {
                Text(text = "다음")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.selectButtonClick()
                    animationState.value = AnimationCode.Feed
                    coroutineScope.launch { pagerState.scrollToPage(1) }
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "선택")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun FeedBuyListPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    PaymongTheme {
        FeedBuyList(animationState, pagerState, coroutineScope, navController)
    }
}