package com.paymong.ui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.paymong.common.navigation.AppNavItem
import com.paymong.ui.app.collect.Collect
import com.paymong.ui.app.collect.CollectPayMong
import com.paymong.ui.app.collect.CollectMap
import com.paymong.ui.app.condition.Condition
import com.paymong.ui.app.help.Help
import com.paymong.ui.app.info_detail.InfoDetail
import com.paymong.ui.app.landing.Landing
import com.paymong.ui.app.login.Login
import com.paymong.ui.app.main.Main
import com.paymong.ui.app.paypoint.PayPoint

@Composable
fun AppMain() {
    Scaffold(
    ) {
        Box(Modifier.padding(it)){
            AppMainNav()
        }
    }
}

@Composable
fun AppMainNav(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppNavItem.Landing.route)
    {
        composable(route = AppNavItem.Landing.route){
            Landing(navController)
        }
        composable(route = AppNavItem.Login.route){
            Login(navController)
        }
        composable(route = AppNavItem.Main.route){
            Main(navController)
        }
        composable(route = AppNavItem.InfoDetail.route + "/{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.StringType
                }
            )
        ) {
            InfoDetail(navController)
        }
        composable(route = AppNavItem.Condition.route + "/{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.StringType
                }
            )
        ) {
            Condition(navController)
        }
        composable(route = AppNavItem.PayPoint.route + "/{memberId}",
            arguments = listOf(
                navArgument("memberId") {
                    type = NavType.StringType
                }
            )
        ) {
            PayPoint(navController)
        }
        composable(route = AppNavItem.Help.route){
            Help(navController)
        }
        composable(route = AppNavItem.Collect.route + "/{memberId}",
            arguments = listOf(
                navArgument("memberId") {
                    type = NavType.StringType
                }
            )
        ) {
            Collect(navController)
        }
        composable(route = AppNavItem.CollectPayMong.route + "/{memberId}",
            arguments = listOf(
                navArgument("memberId") {
                    type = NavType.StringType
                }
            )
        ) {
            CollectPayMong(navController)
        }
        composable(route = AppNavItem.CollectMap.route + "/{memberId}",
            arguments = listOf(
                navArgument("memberId") {
                    type = NavType.StringType
                }
            )
        ) {
            CollectMap(navController)
        }
    }
}