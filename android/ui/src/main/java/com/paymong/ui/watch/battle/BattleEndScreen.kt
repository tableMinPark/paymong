package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MatchingCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun BattleEnd(
    navController: NavHostController,
    battleViewModel: BattleViewModel,
) {
    if (battleViewModel.matchingState == MatchingCode.FINDING){
        navController.navigate(WatchNavItem.BattleLanding.route) {
            popUpTo(0)
            launchSingleTop =true
        }
    }

    val bg = painterResource(R.drawable.battle_bg)
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


            var findCode = ""
            val chCode : CharacterCode
            val player: Painter

            if (battleViewModel.battleActive.order == "A") {
                findCode = battleViewModel.characterCodeA
                chCode = CharacterCode.valueOf(findCode)
                player = painterResource(chCode.resourceCode)

            } else {
                findCode = battleViewModel.characterCodeB
                chCode = CharacterCode.valueOf(findCode)
                player = painterResource(chCode.resourceCode)
            }

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
                Image(painter = win, contentDescription = null, modifier = Modifier.width(150.dp))
            } else {
                val lose = painterResource(R.drawable.lose)
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
            ) }}
    }


}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleEndPreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleEnd(navController, viewModel)
    }
}