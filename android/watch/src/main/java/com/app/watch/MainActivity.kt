package com.app.watch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.app.watch.navigation.NavGraph
import com.app.watch.theme.PaymongTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearPaymongMain()
        }
    }
}

@Composable
fun WearPaymongMain() {
    PaymongTheme {
        NavGraph()
    }
}