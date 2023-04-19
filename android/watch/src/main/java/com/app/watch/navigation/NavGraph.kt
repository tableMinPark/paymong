package com.app.watch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.app.watch.ui.main.Main
import com.app.watch.ui.ending.Ending
import com.app.watch.ui.interaction.activity.*
import com.app.watch.ui.interaction.battle.Battle
import com.app.watch.ui.interaction.battle.BattleActive
import com.app.watch.ui.interaction.battle.BattleEnd
import com.app.watch.ui.interaction.feed.Feed
import com.app.watch.ui.interaction.feed.FeedBuyList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph (){
    val animationState = remember { mutableStateOf("") }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState(1)
    val coroutineScope = rememberCoroutineScope()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.Main.route)
    {
//      Main
        composable( route = NavItem.Main.route) {
            Main(animationState, pagerState, coroutineScope, navController)
        }

        // Feed
        composable( route = NavItem.Feed.route ){
            Feed(navController)
        }
        composable(
            route = NavItem.FeedBuyList.route  + "/{feedItemCategory}",
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
        composable(route = NavItem.Activity.route){
            Activity(navController)
        }
        composable(route = NavItem.TrainingActive.route){
            TrainingActive(navController)
        }
        composable(route = NavItem.TrainingEnd.route){
            TrainingEnd(animationState, pagerState, coroutineScope, navController)
        }
        composable(route = NavItem.WalkingActive.route){
            WalkingACtive(navController)
        }
        composable(route = NavItem.WalkingEnd.route){
            WalkingEnd(animationState, pagerState, coroutineScope, navController)
        }

        // 배틀
        composable(route = NavItem.Battle.route){
            Battle(navController)
        }
        composable(route = NavItem.BattleActive.route){
            BattleActive(navController)
        }
        composable(route = NavItem.BattleEnd.route){
            BattleEnd(animationState, pagerState, coroutineScope, navController)
        }

        // 컬렉션
        composable(route = NavItem.Ending.route){
            Ending(navController)
        }
    }
}