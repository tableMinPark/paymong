package com.paymong.ui.app.collect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.paymong.common.code.SoundCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.ui.app.common.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.dalmoori

@Composable
fun Collect(
    navController: NavController,
    soundViewModel: SoundViewModel
) {
    Scaffold(
        topBar = {TopBar("몽집", navController, AppNavItem.Main.route, soundViewModel)},
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Btn(navController, btnText = "PayMong", AppNavItem.CollectPayMong.route, soundViewModel)
                Btn(navController, btnText = "Map", AppNavItem.CollectMap.route, soundViewModel)
                Btn(navController, btnText = "MongMap", AppNavItem.MongMap.route, soundViewModel)
            }
        }
    }
}

@Composable
fun Btn(navController: NavController, btnText: String, route: String, soundViewModel:SoundViewModel){
    Button(
        onClick = {
            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
            navController.navigate(route){
            popUpTo("collect")
            navController.graph.setStartDestination(AppNavItem.Main.route)
        } },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(40.dp)
            .clip(RoundedCornerShape(15.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(alpha = 0.5f))
    ) {
        Text(text = btnText, textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 40.sp, fontWeight = FontWeight.Bold,
        color = Color.White
        )
    }
}