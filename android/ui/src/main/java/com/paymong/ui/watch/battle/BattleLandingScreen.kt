package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun BattleLanding(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {
    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .clickable {
                navController.navigate(WatchNavItem.BattleWait.route) {
                    popUpTo(0)
                    launchSingleTop =true
                }
                battleViewModel.battleWait()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "PAYMONG",
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                color = PayMongRed200,
                modifier = Modifier.padding(10.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(text = "BATTLE", textAlign = TextAlign.Center)
            val battleTitle = painterResource(R.drawable.battle_title)
            Image(painter = battleTitle, contentDescription = null)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "터치해서 배틀하기",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = dalmoori,
                color = PayMongRed200
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattlePreview() {
    val navController = rememberSwipeDismissableNavController()
    val battleViewModel: BattleViewModel = viewModel()

    PaymongTheme {
        BattleLanding(navController, battleViewModel)
    }
}