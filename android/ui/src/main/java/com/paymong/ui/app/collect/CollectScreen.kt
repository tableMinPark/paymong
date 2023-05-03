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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun Collect(navController: NavController) {
    Scaffold(
        topBar = {TopBar("몽집", navController, AppNavItem.Main.route)},
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Btn(navController, btnText = "PayMong", AppNavItem.CollectPayMong.route)
                Btn(navController, btnText = "Map", AppNavItem.CollectMap.route)
            }
        }
    }
}

@Composable
fun Btn(navController: NavController, btnText: String, route: String){
    Button(
        onClick = { navController.navigate(route){
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

@Preview(showBackground = true)
@Composable
fun CollectPreview() {
    val navController = rememberNavController()
    PaymongTheme {
//        Collect(navController)
        Btn(navController, "PayMong", AppNavItem.CollectPayMong.route)
    }
}