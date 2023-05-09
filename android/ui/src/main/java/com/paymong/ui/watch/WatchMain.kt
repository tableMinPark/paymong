package com.paymong.ui.watch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.paymong.domain.watch.activity.TrainingViewModel
import com.paymong.domain.watch.activity.WalkingViewModel
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.domain.watch.feed.FeedViewModel
import com.paymong.domain.watch.main.MainViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.watch.activity.*
import com.paymong.ui.watch.battle.*
import com.paymong.ui.watch.feed.Feed
import com.paymong.ui.watch.feed.FeedBuyList
import com.paymong.ui.watch.landing.Landing
import com.paymong.ui.watch.main.Main

@Composable
fun WatchMain(watchLandingViewModel : WatchLandingViewModel) {
    PaymongTheme() {
        NavGraph(watchLandingViewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph (watchLandingViewModel : WatchLandingViewModel){
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
            val mainViewModel = viewModel<MainViewModel>(viewModelStoreOwner)
            Main(animationState, pagerState, coroutineScope, navController, mainViewModel)
        }

        // Feed
        composable( route = WatchNavItem.Feed.route){
            val feedViewModel = viewModel<FeedViewModel>(viewModelStoreOwner)
            Feed(navController, feedViewModel)
        }
        composable( route = WatchNavItem.FeedBuyList.route){
            val feedViewModel = viewModel<FeedViewModel>(viewModelStoreOwner)
            FeedBuyList(animationState, pagerState, coroutineScope, navController, feedViewModel)
        }

        // Activity
        composable(route = WatchNavItem.Activity.route){
            Activity(navController)
        }
        composable(route = WatchNavItem.Training.route){
            val trainingViewModel = viewModel<TrainingViewModel>(viewModelStoreOwner)
            TrainingActive(navController, trainingViewModel)
        }
        composable(route = WatchNavItem.Walking.route){
            val walkingViewModel = viewModel<WalkingViewModel>(viewModelStoreOwner)
            WalkingActive(navController, walkingViewModel)
        }

        // Battle
        composable(route = WatchNavItem.Battle.route){
            BattleGraphUI()
        }
    }
}

@Composable
fun BattleGraphUI (){
    val navController = rememberSwipeDismissableNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = WatchNavItem.BattleLanding.route
    ) {
        // Battle
        composable(route = WatchNavItem.BattleLanding.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleLanding(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleWait.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleWait(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleFind.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleFind(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleActive.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleActive(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleSelectBefore.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleSelectBefore(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleSelect.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleSelect(navController, battleViewModel)
        }
        composable(route = WatchNavItem.BattleEnd.route){
            val battleViewModel = viewModel<BattleViewModel>(viewModelStoreOwner)
            BattleEnd(navController, battleViewModel)
        }
    }
}
