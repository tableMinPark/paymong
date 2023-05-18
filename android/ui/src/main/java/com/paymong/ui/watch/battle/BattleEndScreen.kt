package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.MapCode
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.SoundCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.BattleViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.BattleBackgroundGif

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BattleEnd(
    navController: NavHostController,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel,
) {
    Background(MapCode.MP000, false)
    BattleBackgroundGif()

    when (battleViewModel.matchingState) {
        MatchingCode.FINDING -> {
            navController.navigate(WatchNavItem.BattleLanding.route) {
                popUpTo(0)
                launchSingleTop = true
            }
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


            val player = painterResource(battleViewModel.mongCode.resourceCode)
            Image(painter = player, contentDescription = null, modifier = Modifier.width(150.dp))
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(1f)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {
            if (battleViewModel.win) {
                val win = painterResource(R.drawable.win)
                soundViewModel.soundPlay(SoundCode.BATTLE_WIN)
                Image(painter = win, contentDescription = null, modifier = Modifier.width(150.dp))
            } else {
                val lose = painterResource(R.drawable.lose)
                soundViewModel.soundPlay(SoundCode.BATTLE_LOSE)
                Image(painter = lose, contentDescription = null, modifier = Modifier.width(150.dp))
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(30.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blue_bnt),
                    contentDescription = "blue_bnt",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clickable {
                            soundViewModel.soundPlay(SoundCode.BATTLE_BUTTON)
                            navController.navigate(WatchNavItem.BattleLanding.route) {
                                popUpTo(0)
                                launchSingleTop = true
                            }

                        }
                )
                Text(
                    text = "종료",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    color = Color(0xFF0C4DA2)
                )
            }
        }
    }
}