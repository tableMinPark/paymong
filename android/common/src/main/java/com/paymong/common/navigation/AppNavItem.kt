package com.paymong.common.navigation

sealed class AppNavItem(
    val route: String
) {
    object Main: AppNavItem("main")
    object InfoDetail: AppNavItem("info_detail")
    object SmartThings: AppNavItem("smart_things")
    object AddSmartThings: AppNavItem("add_smart_things")
    object Condition: AppNavItem("condition")
    object PayPoint: AppNavItem("pay_point")
    object Help: AppNavItem("help")
    object Collect: AppNavItem("collect")
    object CollectMap: AppNavItem("collect_map")
    object CollectPayMong: AppNavItem("collect_paymong")
    object MongMap: AppNavItem("mong_map")
}