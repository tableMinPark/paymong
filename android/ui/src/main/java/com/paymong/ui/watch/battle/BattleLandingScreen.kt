package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.SoundCode
import com.paymong.domain.watch.BattleViewModel
import com.paymong.domain.watch.SoundViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background

@Composable
fun BattleLanding(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel
) {
    Background(true)

    LaunchedEffect(key1 = 0) {
        battleViewModel.mongId = watchViewModel.mong.mongId
    }

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = if (screenWidthDp < 200) 12 else 15
    var battleImgSize = if (screenWidthDp < 200) 150 else 180


    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .clickable {
                soundViewModel.soundPlay(SoundCode.BATTLE_BUTTON)
                navController.navigate(WatchNavItem.BattleWait.route) {
                    popUpTo(0)
                    launchSingleTop = true
                }
                battleViewModel.battleWait()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(text = "PAYMONG",
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                color = PayMongRed200,
                modifier = Modifier.fillMaxWidth(),
                fontSize = fontSize.sp,

            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(text = "BATTLE", textAlign = TextAlign.Center)
            val battleTitle = painterResource(R.drawable.battle_title)
            Image(painter = battleTitle, contentDescription = null,  modifier = Modifier.width(battleImgSize.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Text(
                text = "터치해서 배틀하기",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = dalmoori,
                color = PayMongRed200,
                fontSize = fontSize.sp,
            )
        }
    }
}
