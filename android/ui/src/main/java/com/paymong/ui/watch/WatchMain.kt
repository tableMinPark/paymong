package com.paymong.ui.watch

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.WatchLandingViewModel
import com.paymong.domain.watch.BattleViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.domain.watch.FeedViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.watch.activity.*
import com.paymong.ui.watch.battle.*
import com.paymong.ui.watch.feed.Feed
import com.paymong.ui.watch.feed.FeedBuyList
import com.paymong.ui.watch.landing.Landing
import com.paymong.ui.watch.main.Main

@Composable
fun WatchMain(
    watchLandingViewModel : WatchLandingViewModel
) {
    PaymongTheme {
        NavGraph(watchLandingViewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph (
    watchLandingViewModel : WatchLandingViewModel
){
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState(1)
    val coroutineScope = rememberCoroutineScope()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = WatchNavItem.Landing.route
    ) {
        // Landing
        composable( route = WatchNavItem.Landing.route) {
            Landing(navController, watchLandingViewModel)
        }

        // Main
        composable( route = WatchNavItem.Main.route) {
            val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            Main(animationState, pagerState, coroutineScope, navController, watchViewModel, soundViewModel)
        }

        // Feed
        composable( route = WatchNavItem.Feed.route){
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            val feedViewModel = viewModel<FeedViewModel>(viewModelStoreOwner)
            Feed(navController, soundViewModel, feedViewModel)
        }
        composable( route = WatchNavItem.FeedBuyList.route){
            val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            val feedViewModel = viewModel<FeedViewModel>(viewModelStoreOwner)
            FeedBuyList(animationState, pagerState, coroutineScope, navController, watchViewModel, soundViewModel, feedViewModel)
        }

        // Activity
        composable(route = WatchNavItem.Activity.route){
            val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            Activity(navController, watchViewModel, soundViewModel)
        }
        composable(route = WatchNavItem.TrainingLanding.route){
            TrainingLanding(navController)
        }
        composable(route = WatchNavItem.Training.route){
            val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            TrainingActive(navController, watchViewModel, soundViewModel)
        }
        composable(route = WatchNavItem.Walking.route){
            val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            WalkingActive(navController, watchViewModel, soundViewModel)
        }

        // Battle
        composable(route = WatchNavItem.Battle.route){
            val watchViewModel = viewModel<WatchViewModel>(viewModelStoreOwner)
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
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
