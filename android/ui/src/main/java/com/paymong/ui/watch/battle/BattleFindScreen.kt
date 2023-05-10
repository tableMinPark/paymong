package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MatchingCode
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.domain.watch.refac.SoundViewModel
import com.paymong.domain.watch.refac.WatchViewModel
import com.paymong.ui.watch.common.Background

@Composable
fun BattleFind(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel
) {
    var mongCode : CharacterCode
    LaunchedEffect(key1 = 0) {
        // player1 :: 위쪽
        if (battleViewModel.battleActive.order == "A") {
            mongCode = CharacterCode.valueOf(battleViewModel.mongCodeB)

        } else {
            findCode = battleViewModel.mongCodeA
            chCode = CharacterCode.valueOf(findCode)
        }
    }
    Background(true)

    var findCode = ""
    var mongResourceCode = painterResource(mongCode.resourceCode)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var characterSize = if (screenWidthDp < 200) 80 else 100

    when(battleViewModel.matchingState) {
        MatchingCode.ACTIVE -> {
            navController.navigate(WatchNavItem.BattleActive.route) {
                popUpTo(0)
                launchSingleTop =true
            }
            battleViewModel.battleActive()
        }
        else -> {}
    }

    Button(
        onClick = { navController.navigate(WatchNavItem.BattleActive.route) },
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ){}

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
//            battleViewModel.battleEntity?.let { Text(text = it.battleRoomId, textAlign = TextAlign.Center) }

            Image(painter = mongResourceCode, contentDescription = null, modifier = Modifier
                .width(characterSize.dp)
                .height(characterSize.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            val battle = painterResource(R.drawable.battle)
            Image(painter = battle, contentDescription = null)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
//            modifier = Modifier.fillMaxWidth()
        ) {
//            battleViewModel.battleEntity?.let { Text(text = it.battleRoomId, textAlign = TextAlign.Center) }

            // player2 :: 아래쪽

            var findCode = ""
            val chCode : CharacterCode
            val player2: Painter

            if (battleViewModel.battleActive.order == "A") {
                findCode = battleViewModel.mongCodeA
                chCode = CharacterCode.valueOf(findCode)
                player2 = painterResource(chCode.resourceCode)

            } else {
                findCode = battleViewModel.mongCodeB
                chCode = CharacterCode.valueOf(findCode)
                player2 = painterResource(chCode.resourceCode)
            }
            Image(painter = player2, contentDescription = null, modifier = Modifier
                .width(characterSize.dp)
                .height(characterSize.dp))


            }

    }
}