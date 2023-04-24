package com.paymong.ui.watch.battle

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun BattleSelectBefore(
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
            Text(text = "어느쪽으로?", textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.BattleSelect.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "다음으로")
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleSelectBeforePreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleSelectBefore(navController)
    }
}