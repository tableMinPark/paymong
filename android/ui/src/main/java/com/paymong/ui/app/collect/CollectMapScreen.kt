package com.paymong.ui.app.collect

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.paymong.domain.app.collect.CollectMapViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun CollectMap(navController: NavController) {
    val viewModel: CollectMapViewModel = viewModel()
    CollectMapUI(navController, viewModel)
}

@Composable
fun CollectMapUI(
    navController: NavController,
    viewModel: CollectMapViewModel
) {
    Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center
) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Map", fontFamily = dalmoori, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                    modifier = Modifier.height(80.dp),
                    actions = {
                        IconButton(onClick = { navController.navigate(AppNavItem.Collect.route + "/${viewModel.memberId}") }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                        }
                    },
                    backgroundColor = PayMongPurple,
                    elevation = 40.dp
                ) },
            backgroundColor = PayMongNavy
        ) {
            Box(Modifier.padding(it)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "본문", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectMapPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        CollectMap(navController)
    }
}