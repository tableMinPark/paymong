package com.paymong.ui.app.help

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.HelpViewModel
import com.paymong.ui.app.collect.Btn
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PaymongTheme

@Composable
fun Help(
    navController: NavController,
    soundViewModel: SoundViewModel
) {
    Scaffold(
        topBar = { TopBar("도움말", navController, AppNavItem.Main.route, soundViewModel) },
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)){
            BgGif()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpPreview() {
    val navController = rememberNavController()
    val soundViewModel:SoundViewModel = viewModel()
    PaymongTheme {
        Help(navController, soundViewModel)
    }
}