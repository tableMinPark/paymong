package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.landing.MainBackgroundGif

@Composable
fun BattleSelectBefore(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = 0
    var attackDefenceSize = 0

    if (screenWidthDp < 200) {
        fontSize = 20
        attackDefenceSize = 60

    }
    else {
        fontSize = 25
        attackDefenceSize = 70
    }

    if (battleViewModel.matchingState == MatchingCode.SELECT){
        navController.navigate(WatchNavItem.BattleSelect.route) {
            popUpTo(0)
            launchSingleTop =true
        }
        battleViewModel.battleSelect()
    } else if (battleViewModel.matchingState == MatchingCode.END){
        navController.navigate(WatchNavItem.BattleEnd.route) {
            popUpTo(0)
            launchSingleTop =true
        }
        battleViewModel.battleEnd()
    }

    val bg = painterResource(R.drawable.battle_bg)
    val defence = painterResource(R.drawable.defence)
    val attack = painterResource(R.drawable.attack)

    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    BattleBackgroundGif()


    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {


        if (battleViewModel.battleActive.order == "A") {
            if (battleViewModel.battleActive.nextAttacker == "A") {
                Image(
                    painter = attack, contentDescription = null,
                    modifier = Modifier.size(attackDefenceSize.dp)
                )
            } else {
                Image(
                    painter = defence, contentDescription = null,
                    modifier = Modifier.size(attackDefenceSize.dp)
                )

            }
        } else {
            if (battleViewModel.battleActive.nextAttacker == "A") {
                Image(
                    painter = defence, contentDescription = null,
                    modifier = Modifier.size(attackDefenceSize.dp)
                )
            } else {

                Image(
                    painter = attack, contentDescription = null,
                    modifier = Modifier.size(attackDefenceSize.dp)
                )
            }
        }
    }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleSelectBeforePreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel: BattleViewModel = viewModel()

    PaymongTheme {
        BattleSelectBefore(navController ,viewModel)
    }
}

