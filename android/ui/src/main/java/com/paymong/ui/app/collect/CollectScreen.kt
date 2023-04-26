package com.paymong.ui.app.collect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.collect.CollectViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori


@Composable
fun Collect(navController: NavController) {
    val viewModel: CollectViewModel = viewModel()
    CollectUI(navController, viewModel)
}

@Composable
fun Btn(navController: NavController, btnText: String, route: String){
    Button(
        onClick = { navController.navigate(route) },
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(30.dp)
            .clip(RoundedCornerShape(15.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(alpha = 0.5f))
    ) {
        Text(text = btnText, textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 30.sp, fontWeight = FontWeight.Bold,
        color = Color.White
        )
//        Text(text = btnText)
    }
}

@Composable
fun CollectUI(
    navController: NavController,
    viewModel: CollectViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("몽집", fontFamily = dalmoori, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                modifier = Modifier.height(80.dp),
                actions = {
                    IconButton(onClick = { navController.navigate(AppNavItem.Main.route) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                backgroundColor = PayMongPurple,
                elevation = 40.dp
            ) },
            backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Btn(navController, btnText = "PayMong", AppNavItem.CollectPayMong.route + "/${viewModel.memberId}")
                Btn(navController, btnText = "Map", AppNavItem.CollectMap.route + "/${viewModel.memberId}")
            }
        }
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