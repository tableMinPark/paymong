package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.BattleViewModel
import com.paymong.domain.watch.SoundViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.BattleBackgroundGif

@Composable
fun BattleSelectBefore(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel
) {
    Background(false)
    BattleBackgroundGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = if (screenWidthDp < 200) 20 else 25
    var attackDefenceSize = if (screenWidthDp < 200) 60 else 70

    val defence = painterResource(R.drawable.defence)
    val attack = painterResource(R.drawable.attack)

    when(battleViewModel.matchingState) {
        MatchingCode.SELECT -> {
            battleViewModel.nextAttacker = battleViewModel.battleActive.nextAttacker
            navController.navigate(WatchNavItem.BattleSelect.route) {
                popUpTo(0)
                launchSingleTop =true
            }
            battleViewModel.battleSelect()
        }
        MatchingCode.END -> {
            navController.navigate(WatchNavItem.BattleEnd.route) {
                popUpTo(0)
                launchSingleTop =true
            }
            battleViewModel.battleEnd()
        }
        else -> {}
    }

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