package com.app.paymong.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.paymong.ui.ending.Ending
import com.app.paymong.ui.ending.EndingDetail
import com.app.paymong.ui.login.Login
import com.app.paymong.ui.main.Main

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavItem.Main.route)
    {
        composable(route = NavItem.Main.route){
            Main(navController)
        }
        composable(route = NavItem.Ending.route){
            Login(navController)
        }
        composable(route = NavItem.Ending.route){
            Ending(navController)
        }
        composable(
            route = NavItem.EndingDetail.route + "/{characterId}",
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