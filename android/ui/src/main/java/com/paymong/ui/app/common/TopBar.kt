package com.paymong.ui.app.common

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.code.SoundCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun TopBar(
    msg: String,
    navController: NavController,
    route: String,
    soundViewModel:SoundViewModel,
) {
    TopAppBar(
        title = { Text(msg, fontFamily = dalmoori, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White) },
        modifier = Modifier.height(80.dp),
        actions = {
            IconButton(onClick = {
                soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                navController.navigate(route){
                navController.graph.startDestinationRoute?.let {
                    popUpTo(it)
                }
                launchSingleTop = true
            } }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        },
        backgroundColor = PayMongPurple,
        elevation = 40.dp
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val navController = rememberNavController()
    val soundViewModel:SoundViewModel = viewModel()
    PaymongTheme {
        TopBar("MSG", navController, AppNavItem.Main.route, soundViewModel)
    }
}