package com.paymong.ui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.AppViewModel
import com.paymong.domain.app.CollectMapViewModel
import com.paymong.domain.app.AppLandinglViewModel
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
import com.paymong.ui.app.things.AddSmartThings
import com.paymong.ui.app.things.SmartThings

@Composable
fun AppMain(appLandinglViewModel : AppLandinglViewModel) {
    Scaffold(
    ) {
        Box(Modifier.padding(it)){
            AppMainNav(appLandinglViewModel)
        }
    }
}

@Composable
fun AppMainNav(appLandinglViewModel : AppLandinglViewModel){
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)

    NavHost(
        navController = navController,
        startDestination = AppNavItem.Landing.route)
    {
        composable(route = AppNavItem.Landing.route){
            Landing(navController, appLandinglViewModel)
        }
        composable(route = AppNavItem.Login.route){
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            Login(navController, appLandinglViewModel, soundViewModel)
        }
        composable(route = AppNavItem.Main.route){
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            val appViewModel = viewModel<AppViewModel>(viewModelStoreOwner)
            Main(navController, appViewModel, soundViewModel)
        }
        composable(route = AppNavItem.InfoDetail.route) {
            val appViewModel = viewModel<AppViewModel>(viewModelStoreOwner)
            InfoDetail(navController, appViewModel)
        }
        composable(route = AppNavItem.SmartThings.route){
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            SmartThings(navController, soundViewModel)
        }
        composable(route = AppNavItem.AddSmartThings.route){
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            AddSmartThings(navController, soundViewModel)
        }
        composable(route = AppNavItem.Condition.route) {
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            Condition(navController, soundViewModel)
        }
        composable(route = AppNavItem.PayPoint.route) {
            PayPoint(navController)
        }
        composable(route = AppNavItem.Help.route){
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            Help(navController, soundViewModel)
        }
        composable(route = AppNavItem.Collect.route) {
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            Collect(navController, soundViewModel)
        }
        composable(route = AppNavItem.CollectPayMong.route) {
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            CollectPayMong(navController, soundViewModel)
        }
        composable(route = AppNavItem.CollectMap.route) {
            val mapViewModel = viewModel<CollectMapViewModel>(viewModelStoreOwner)
            val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
            CollectMap(navController, mapViewModel, soundViewModel)
        }
    }
}