package com.paymong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.ui.theme.PaymongTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaymongTheme {
                PaymongMain()
            }
        }
    }

    @Composable
    private fun PaymongMain() {
        val navController = rememberNavController()
        Scaffold(
//        bottomBar = { BottomNavigationBar(navController = navController) }
        ) {
            Box(Modifier.padding(it)){
                NavGraph(navController)
            }
        }
    }
}


