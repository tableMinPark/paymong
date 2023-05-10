package com.paymong.ui.watch.battle

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.ToastMessage
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.domain.watch.refac.SoundViewModel
import com.paymong.domain.watch.refac.WatchViewModel
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.LoadingGif
import com.paymong.ui.watch.common.showToast


@Composable
fun BattleWait(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel
) {
    Background(true)
    LoadingGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = if (screenWidthDp < 200) 12 else 15

    // 매칭 여부 확인해서 화면 이동
    when (battleViewModel.matchingState) {
        MatchingCode.FOUND -> {
            navController.navigate(WatchNavItem.BattleFind.route) {
                popUpTo(0)
            }
            battleViewModel.battleFind()
        }
        MatchingCode.NOT_FOUND -> {
            navController.navigate(WatchNavItem.BattleLanding.route){
                popUpTo(0)
            }
            battleViewModel.battleFindFail()
            showToast(LocalContext.current, ToastMessage.BATTLE_NOT_MATCHING)
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
            Text(
                text = "...배틀을 찾는 중...",
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = fontSize.sp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
