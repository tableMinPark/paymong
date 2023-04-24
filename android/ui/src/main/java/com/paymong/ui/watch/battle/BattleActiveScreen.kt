package com.paymong.ui.watch.battle

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.theme.PaymongTheme

@Composable
fun BattleActive(
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "배틀 진행 화면", textAlign = TextAlign.Center)
        }
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = selectState.value, textAlign = TextAlign.Center)
//        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.BattleSelectBefore.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "선택")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.BattleEnd.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "배틀 끝")
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleActivePreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleActive(navController)
    }
}