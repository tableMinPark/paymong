package com.paymong.ui.watch.interaction.activity

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.theme.PaymongTheme

@Composable
fun Activity(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.TrainingActive.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "훈련")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ){
            Button(
                onClick = { navController.navigate(WatchNavItem.WalkingActive.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "산책")
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun ActivityPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        Activity(navController)
    }
}