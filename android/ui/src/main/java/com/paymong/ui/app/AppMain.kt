package com.paymong.ui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.app.AppViewModel
import com.paymong.domain.app.CollectMapViewModel
import com.paymong.domain.watch.battle.BattleViewModel
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
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

    NavHost(
        navController = navController,
        startDestination = AppNavItem.Landing.route)
    {
        composable(route = AppNavItem.Landing.route){
            val appViewModel = viewModel<AppViewModel>(viewModelStoreOwner)
            Landing(navController, appViewModel)
        }
        composable(route = AppNavItem.Login.route){
            Login(navController)
        }
        composable(route = AppNavItem.Main.route){
            val appViewModel = viewModel<AppViewModel>(viewModelStoreOwner)
            Main(navController, appViewModel)
        }
        composable(route = AppNavItem.InfoDetail.route) {
            val appViewModel = viewModel<AppViewModel>(viewModelStoreOwner)
            InfoDetail(navController, appViewModel)
        }
        composable(route = AppNavItem.Condition.route) {
            Condition(navController)
        }
        composable(route = AppNavItem.PayPoint.route) {
            PayPoint(navController)
        }
        composable(route = AppNavItem.Help.route){
            Help()
        }
        composable(route = AppNavItem.Collect.route) {
            Collect(navController)
        }
        composable(route = AppNavItem.CollectPayMong.route) {
            CollectPayMong(navController)
        }
        composable(route = AppNavItem.CollectMap.route) {
            val mapViewModel = viewModel<CollectMapViewModel>(viewModelStoreOwner)
            CollectMap(navController, mapViewModel)
        }
    }
}