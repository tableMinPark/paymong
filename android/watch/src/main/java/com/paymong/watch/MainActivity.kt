package com.paymong.watch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.paymong.ui.theme.PaymongTheme

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
    PaymongTheme() {
        NavGraph()
    }
}