package com.app.paymong.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    val route: String
) {
    object Main: NavItem("main")
    object Login: NavItem("login")
    object Ending: NavItem("ending")
    object EndingDetail: NavItem("ending_detail")
}