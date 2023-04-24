package com.paymong.common.navigation

sealed class WatchNavItem(val route: String) {
    object Main: WatchNavItem("main")

    object Feed: WatchNavItem("feed")
    object FeedBuyList: WatchNavItem("feed_buy_list")

    object Activity: WatchNavItem("activity")
    object Training: WatchNavItem("training")
    object Walking: WatchNavItem("walking")

    object Battle: WatchNavItem("battle")
    object BattleWait: WatchNavItem("battle_wait")
    object BattleFind: WatchNavItem("battle_find")
    object BattleActive: WatchNavItem("battle_active")
    object BattleSelectBefore: WatchNavItem("battle_select_before")
    object BattleSelect: WatchNavItem("battle_select")
    object BattleEnd: WatchNavItem("battle_end")
}