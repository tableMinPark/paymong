package com.paymong.ui.watch.battle

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.ProgressIndicatorDefaults
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.MessageType
import com.paymong.common.code.SoundCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.BattleViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.BattleBackgroundGif

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BattleSelect(
    navController: NavHostController,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel
) {
    Background(false)
    BattleBackgroundGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val leftRightBtn = if (screenWidthDp < 200) 30 else 40

    when(battleViewModel.matchingState) {
        MatchingCode.SELECT_AFTER -> {
            navController.navigate(WatchNavItem.BattleActive.route) {
                popUpTo(0)
                launchSingleTop =true
            }
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

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,

            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.49f)

        ) {
            Button(
                onClick = {
                    soundViewModel.soundPlay(SoundCode.BATTLE_BUTTON)
                    battleViewModel.select(MessageType.LEFT)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leftbnt ),
                    contentDescription = "leftbnt",
                    modifier = Modifier.size(leftRightBtn.dp)
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .width(5.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(1f)
        ) {
            Button(
                onClick = {
                    soundViewModel.soundPlay(SoundCode.BATTLE_BUTTON)
                    battleViewModel.select(MessageType.RIGHT)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()

            ) {
                Image(
                    painter = painterResource(id = R.drawable.rightbnt ),
                    contentDescription = "rightbnt",
                    modifier = Modifier.size(leftRightBtn.dp)
                )
            }
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = battleViewModel.battleSelectTime.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    CircularProgressIndicator(
        progress = animatedProgress,
        modifier = Modifier.fillMaxSize(),
        startAngle = 271f,
        endAngle = 270f,
        strokeWidth = 4.dp
    )
}