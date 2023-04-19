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
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.watch.ending.Ending
import com.paymong.ui.watch.interaction.activity.*
import com.paymong.ui.watch.interaction.battle.Battle
import com.paymong.ui.watch.interaction.battle.BattleActive
import com.paymong.ui.watch.interaction.battle.BattleEnd
import com.paymong.ui.watch.interaction.feed.Feed
import com.paymong.ui.watch.interaction.feed.FeedBuyList
import com.paymong.ui.watch.main.Main

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph (){
    val animationState = remember { mutableStateOf("") }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState(1)
    val coroutineScope = rememberCoroutineScope()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = WatchNavItem.Main.route)
    {
        // Main
        composable( route = WatchNavItem.Main.route) {
            Main(animationState, pagerState, coroutineScope, navController)
        }

        // Feed
        composable( route = WatchNavItem.Feed.route ){
            Feed(navController)
        }
        composable(
            route = WatchNavItem.FeedBuyList.route  + "/{feedItemCategory}",
            arguments = listOf(
                navArgument("feedItemCategory") {
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            val item = backStackEntry.arguments?.getString("feedItemCategory")

            item?.let {
                FeedBuyList(animationState, item, pagerState, coroutineScope, navController)
            }
        }

        // 활동
        composable(route = WatchNavItem.Activity.route){
            Activity(navController)
        }
        composable(route = WatchNavItem.TrainingActive.route){
            TrainingActive(navController)
        }
        composable(route = WatchNavItem.TrainingEnd.route){
            TrainingEnd(animationState, pagerState, coroutineScope, navController)
        }
        composable(route = WatchNavItem.WalkingActive.route){
            WalkingACtive(navController)
        }
        composable(route = WatchNavItem.WalkingEnd.route){
            WalkingEnd(animationState, pagerState, coroutineScope, navController)
        }

        // 배틀
        composable(route = WatchNavItem.Battle.route){
            Battle(navController)
        }
        composable(route = WatchNavItem.BattleActive.route){
            BattleActive(navController)
        }
        composable(route = WatchNavItem.BattleEnd.route){
            BattleEnd(animationState, pagerState, coroutineScope, navController)
        }

        // 컬렉션
        composable(
            route = WatchNavItem.Ending.route  + "/{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")

            characterId?.let {
                Ending(characterId, navController)
            }
        }

    }
}