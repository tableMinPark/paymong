package com.paymong.common.navigation

sealed class WatchNavItem(val route: String) {
    object Main: WatchNavItem("main")

    object Feed: WatchNavItem("feed")
    object FeedBuyList: WatchNavItem("feed_buy_list")

    object Activity: WatchNavItem("activity")
    object TrainingActive: WatchNavItem("training_active")
    object TrainingEnd: WatchNavItem("training_end")
    object WalkingActive: WatchNavItem("walking_active")
    object WalkingEnd: WatchNavItem("walking_end")

    object Battle: WatchNavItem("battle")
    object BattleActive: WatchNavItem("battle_active")
    object BattleEnd: WatchNavItem("battle_end")

    object Ending: WatchNavItem("ending")
}