package com.paymong

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.paymong.common.navigation.AppNavItem
import com.paymong.ui.app.ending.Ending
import com.paymong.ui.app.ending.EndingDetail
import com.paymong.ui.app.login.Login
import com.paymong.ui.app.main.Main

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = AppNavItem.Main.route)
    {
        composable(route = AppNavItem.Main.route){
            Main(navController)
        }
        composable(route = AppNavItem.Ending.route){
            Login(navController)
        }
        composable(route = AppNavItem.Ending.route){
            Ending(navController)
        }
        composable(
            route = AppNavItem.EndingDetail.route + "/{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")

            characterId?.let {
                EndingDetail(characterId, navController)
            }
        }
    }
}