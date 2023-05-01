package com.paymong.ui.watch.battle

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import kotlinx.coroutines.CoroutineScope

@Composable
fun BattleSelectBefore(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = 0


    if (screenWidthDp < 200) {
        fontSize = 20


    }
    else {
        fontSize = 25

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
            Text(text = "어느쪽으로?", textAlign = TextAlign.Center,
            fontFamily = dalmoori,
            fontSize = fontSize.sp)
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