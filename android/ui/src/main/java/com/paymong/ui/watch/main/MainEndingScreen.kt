package com.paymong.ui.watch.main

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.theme.PaymongTheme

@Composable
fun MainEnding(navController: NavHostController) {
    val scrollScope = rememberScrollState()

    val idList = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6"
    )

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().verticalScroll(scrollScope)
    ) {
        idList.forEach { id ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate(WatchNavItem.Ending.route + "/$id") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = id)
                }
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainEndingPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        MainEnding(navController)
    }
}