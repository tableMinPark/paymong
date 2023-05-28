package com.paymong.ui.watch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.LandingCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.SocketCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.watch.*
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.activity.*
import com.paymong.ui.watch.battle.*
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.Loading
import com.paymong.ui.watch.feed.Feed
import com.paymong.ui.watch.feed.FeedBuyList
import com.paymong.ui.watch.landing.Landing
import com.paymong.ui.watch.main.Main
import kotlinx.coroutines.delay

@Composable
fun WatchMain(
    watchLandingViewModel : WatchLandingViewModel
) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)

    PaymongTheme {
        if (watchLandingViewModel.landingCode == LandingCode.SUCCESS) {
            when(watchViewModel.socketState) {
                SocketCode.DISCONNECT -> {
                    SocketError(watchViewModel)
                }
                SocketCode.LOADING -> {
                    LaunchedEffect(true) {
                        delay(1000)
                        watchViewModel.socketConnect()
                    }
                    Loading()
                }
                else -> {
                    WatchNavGraph()
                }
            }
        } else {
            Landing(watchLandingViewModel)
        }
    }
}
@Composable
fun SocketError(
    watchViewModel: WatchViewModel
) {
    Background(MapCode.MP000)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val fontSize = if (screenWidthDp < 200) 12 else 15

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { watchViewModel.socketState = SocketCode.LOADING },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "서버에 연결할 수 없습니다.\n\n터치해서\n\n재연결 시도 하기",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = dalmoori,
            color = PayMongRed200,
            fontSize = fontSize.sp,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WatchNavGraph (){
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState(1)
    val coroutineScope = rememberCoroutineScope()

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)
    val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = WatchNavItem.Main.route
    ) {
        // Main
        composable( route = WatchNavItem.Main.route) {
            Main(animationState, pagerState, coroutineScope, navController, watchViewModel, soundViewModel)
        }

        // Feed
        composable( route = WatchNavItem.Feed.route){
            val feedViewModel = viewModel<FeedViewModel>(viewModelStoreOwner)
            Feed(navController, soundViewModel, feedViewModel)
        }
        composable( route = WatchNavItem.FeedBuyList.route){
            val feedViewModel = viewModel<FeedViewModel>(viewModelStoreOwner)
            FeedBuyList(animationState, pagerState, coroutineScope, navController, watchViewModel, soundViewModel, feedViewModel)
        }

        // Activity
        composable(route = WatchNavItem.Activity.route){
            Activity(navController, watchViewModel, soundViewModel)
        }
        composable(route = WatchNavItem.TrainingLanding.route){
            TrainingLanding(navController)
        }
        composable(route = WatchNavItem.Training.route){
            TrainingActive(navController, watchViewModel, soundViewModel)
        }
        composable(route = WatchNavItem.Walking.route){
            WalkingActive(navController, watchViewModel, soundViewModel)
        }

        // Battle
        composable(route = WatchNavItem.Battle.route){
            BattleGraphUI(watchViewModel, soundViewModel)
        }
    }
}

@Composable
fun BattleGraphUI (
    watchViewModel : WatchViewModel,
    soundViewModel: SoundViewModel
){
    val navController = rememberSwipeDismissableNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = WatchNavItem.BattleLanding.route
    ) {
        // Battle
        composable(route = WatchNavItem.BattleLanding.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleLanding(navController, watchViewModel, soundViewModel, battleViewModel)
        }
        composable(route = WatchNavItem.BattleWait.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleWait(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleFind.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleFind(navController, soundViewModel, battleViewModel)
        }
        composable(route = WatchNavItem.BattleActive.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleActive(navController, soundViewModel, battleViewModel)
        }
        composable(route = WatchNavItem.BattleSelectBefore.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleSelectBefore(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleSelect.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleSelect(navController, soundViewModel, battleViewModel)
        }
        composable(route = WatchNavItem.BattleEnd.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleEnd(navController, soundViewModel, battleViewModel)
        }
    }
}
