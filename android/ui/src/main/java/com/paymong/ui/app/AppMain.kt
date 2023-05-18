package com.paymong.ui.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Text
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.SocketCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.*
import com.paymong.ui.app.collect.Collect
import com.paymong.ui.app.collect.CollectPayMong
import com.paymong.ui.app.collect.CollectMap
import com.paymong.ui.app.collect.MongMap
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.app.condition.Condition
import com.paymong.ui.app.help.Help
import com.paymong.ui.app.info_detail.InfoDetail
import com.paymong.ui.app.landing.Landing
import com.paymong.ui.app.login.Login
import com.paymong.ui.app.main.Main
import com.paymong.ui.app.paypoint.PayPoint
import com.paymong.ui.app.things.AddSmartThings
import com.paymong.ui.app.things.SmartThings
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.LoadingGif

@Composable
fun AppMain(
    appLandingViewModel : AppLandinglViewModel
) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val appViewModel = viewModel<AppViewModel>(viewModelStoreOwner)

    if (appViewModel.isSocketConnect == SocketCode.DISCONNECT) {
        appViewModel.reconnectSocket()
    }

    PaymongTheme {
        Scaffold(
        ) {
            Box(Modifier.padding(it)) {
                if (appViewModel.isSocketConnect == SocketCode.CONNECT ||
                    appViewModel.isSocketConnect == SocketCode.NOT_TOKEN) {
                    AppMainNav(appLandingViewModel)
                } else {
                    SocketError(appViewModel.isSocketConnect)
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SocketError(
    isSocketConnect : SocketCode
) {
    BgGif()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
            },
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val logo = painterResource(R.drawable.app_logo)
            Image(
                painter = logo, contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .padding(80.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (isSocketConnect == SocketCode.DISCONNECT ||
                isSocketConnect == SocketCode.LOADING) {
                val loadBarSize = 75
                Box(
                    modifier = Modifier
                        .width(loadBarSize.dp)
                        .height(loadBarSize.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    LoadingGif()
                }
            }
        }
    }
}


@Composable
fun AppMainNav(appLandingViewModel : AppLandinglViewModel){
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val appViewModel = viewModel<AppViewModel>(viewModelStoreOwner)
    val soundViewModel = viewModel<SoundViewModel>(viewModelStoreOwner)
    val mapViewModel = viewModel<CollectMapViewModel>(viewModelStoreOwner)
    val smartThingsViewModel = viewModel<SmartThingsViewModel>(viewModelStoreOwner)

    NavHost(
        navController = navController,
        startDestination = AppNavItem.Landing.route)
    {
        composable(route = AppNavItem.Landing.route){
            Landing(navController, appLandingViewModel)
        }
        composable(route = AppNavItem.Login.route){
            Login(navController, appLandingViewModel)
        }
        composable(route = AppNavItem.Main.route){
            Main(navController, appViewModel, soundViewModel)
        }
        composable(route = AppNavItem.InfoDetail.route) {
            InfoDetail(navController, appViewModel)
        }
        composable(route = AppNavItem.SmartThings.route){
            SmartThings(navController, soundViewModel, smartThingsViewModel)
        }
        composable(route = AppNavItem.AddSmartThings.route){
            AddSmartThings(navController, soundViewModel, smartThingsViewModel)
        }
        composable(route = AppNavItem.Condition.route) {
            Condition(navController, soundViewModel)
        }
        composable(route = AppNavItem.PayPoint.route) {
            PayPoint(navController)
        }
        composable(route = AppNavItem.Help.route){
            Help(navController, soundViewModel)
        }
        composable(route = AppNavItem.Collect.route) {
            Collect(navController, soundViewModel)
        }
        composable(route = AppNavItem.CollectPayMong.route) {
            CollectPayMong(navController, soundViewModel)
        }
        composable(route = AppNavItem.CollectMap.route) {
            CollectMap(navController, mapViewModel, soundViewModel)
        }
        composable(route = AppNavItem.MongMap.route) {
            MongMap(navController, soundViewModel)
        }
    }
}