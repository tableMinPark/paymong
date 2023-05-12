package com.paymong.common.navigation

sealed class AppNavItem(
    val route: String
) {
    object Landing: AppNavItem("landing")
    object Login: AppNavItem("login")
    object Main: AppNavItem("main")
    object InfoDetail: AppNavItem("info_detail")
    object SmartThings: AppNavItem("smart_things")
    object AddSmartThings: AppNavItem("add_smart_things")
    object Condition: AppNavItem("condition")
    object PayPoint: AppNavItem("pay_point")
    object Help: AppNavItem("help")
    object CollectSelect: AppNavItem("collect_select")
    object Collect: AppNavItem("collect")
    object CollectMap: AppNavItem("collect_map")
    object CollectPayMong: AppNavItem("collect_paymong")
}