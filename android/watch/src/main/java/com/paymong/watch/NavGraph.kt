package com.paymong.watch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.watch.activity.*
import com.paymong.ui.watch.battle.*
import com.paymong.ui.watch.feed.Feed
import com.paymong.ui.watch.feed.FeedBuyList
import com.paymong.ui.watch.main.Main

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph (){
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState(1)
    val coroutineScope = rememberCoroutineScope()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = WatchNavItem.Main.route
    ) {
        // Main
        composable( route = WatchNavItem.Main.route) {
            Main(animationState, pagerState, coroutineScope, navController)
        }

        // Feed
        composable( route = WatchNavItem.Feed.route ){
            Feed(navController)
        }
        composable(
            route = WatchNavItem.FeedBuyList.route  + "/{foodCategory}",
            arguments = listOf(
                navArgument("foodCategory") {
                    type = NavType.StringType
                }
            )
        ){
            FeedBuyList(animationState, pagerState, coroutineScope, navController)
        }

        // Activity
        composable(route = WatchNavItem.Activity.route){
            Activity(navController)
        }
        composable(route = WatchNavItem.Training.route){
            TrainingActive(navController)
        }
        composable(route = WatchNavItem.Walking.route){
            WalkingActive(navController)
        }

        // Battle
        composable(route = WatchNavItem.Battle.route){
            Battle(navController)
        }
        composable(route = WatchNavItem.BattleWait.route){
            BattleWait(navController)
        }
        composable(route = WatchNavItem.BattleFind.route){
            BattleFind(navController)
        }
        composable(route = WatchNavItem.BattleActive.route){
            BattleActive(navController)
        }
        composable(route = WatchNavItem.BattleSelectBefore.route){
            BattleSelectBefore(navController)
        }
        composable(route = WatchNavItem.BattleSelect.route){
            BattleSelect(navController)
        }
        composable(route = WatchNavItem.BattleEnd.route){
            BattleEnd(navController)
        }
    }
}