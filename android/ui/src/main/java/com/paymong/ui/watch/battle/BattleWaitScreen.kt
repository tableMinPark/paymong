package com.paymong.ui.watch.battle

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.ToastMessage
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun BattleWait(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {
    // 매칭 여부 확인해서 화면 이동
    if (battleViewModel.matchingState == MatchingCode.FOUND){
        navController.navigate(WatchNavItem.BattleFind.route) {
            popUpTo(0)
        }
        battleViewModel.battleFind()
    } else if (battleViewModel.matchingState == MatchingCode.NOT_FOUND){
        navController.navigate(WatchNavItem.BattleLanding.route){
            popUpTo(0)
        }
        battleViewModel.battleFindFail()
        Toast.makeText(LocalContext.current, ToastMessage.BATTLE_NOT_MATCHING.message, Toast.LENGTH_LONG).show()
    }

    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)

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
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleWaitPreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleWait(navController, viewModel)
    }
}