package com.paymong.ui.watch.battle

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleEndViewModel
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun BattleEnd(
    navController: NavHostController
) {
    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)

    val viewModel : BattleEndViewModel = viewModel()
    val result = viewModel.win

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            var findCode = viewModel.characterCode
            var chCode = CharacterCode.valueOf(findCode)
            val charac = painterResource(chCode.code)
            Image(painter = charac, contentDescription = null, modifier = Modifier.width(150.dp))
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (result) {
                val win = painterResource(R.drawable.win)
                Image(painter = win, contentDescription = null, modifier = Modifier.width(150.dp))
            } else {
                val lose = painterResource(R.drawable.lose)
                Image(painter = lose, contentDescription = null, modifier = Modifier.width(150.dp))
            }
        }
    }

    Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate(WatchNavItem.Main.route){
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop =true
        }
    },2000)
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleEndPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleEnd(navController)
    }
}