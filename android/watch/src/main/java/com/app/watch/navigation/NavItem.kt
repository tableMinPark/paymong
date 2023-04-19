package com.app.watch.navigation

sealed class NavItem(val route: String) {
    object Main: NavItem("main")

    object Feed: NavItem("feed")
    object FeedBuyList: NavItem("feed_buy_list")

    object Activity: NavItem("activity")
    object TrainingActive: NavItem("training_active")
    object TrainingEnd: NavItem("training_end")
    object WalkingActive: NavItem("walking_active")
    object WalkingEnd: NavItem("walking_end")

    object Battle: NavItem("battle")
    object BattleActive: NavItem("battle_active")
    object BattleEnd: NavItem("battle_end")

    object Ending: NavItem("ending")
}