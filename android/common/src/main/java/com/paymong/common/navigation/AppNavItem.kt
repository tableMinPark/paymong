package com.paymong.common.navigation

sealed class AppNavItem(
    val route: String
) {
    object Main: AppNavItem("main")
    object Login: AppNavItem("login")
    object Ending: AppNavItem("ending")
    object EndingDetail: AppNavItem("ending_detail")
}