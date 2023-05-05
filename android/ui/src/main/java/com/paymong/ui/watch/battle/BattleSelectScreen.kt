package com.paymong.ui.watch.battle

import android.media.SoundPool
import android.os.SystemClock
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.ProgressIndicatorDefaults
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.MessageType
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun BattleSelect(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {


    val soundPool = SoundPool.Builder()
        .setMaxStreams(1) // 동시에 재생 가능한 스트림의 최대 수
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)


    fun ButtonSoundPlay () {
        println("사운드 재생")
        val waitLimit = 1000
        var waitCounter = 0
        var throttle = 10
        while (soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f) == 0 && waitCounter < waitLimit){
            waitCounter++
            SystemClock.sleep(throttle.toLong())
        }
    }


    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var leftRightBtn = 0


    if (screenWidthDp < 200) {
        leftRightBtn = 30
    }
    else {
        leftRightBtn = 40
    }


    if (battleViewModel.matchingState == MatchingCode.SELECT_AFTER){
        navController.navigate(WatchNavItem.BattleActive.route) {
            popUpTo(0)
            launchSingleTop =true
        }
    } else if (battleViewModel.matchingState == MatchingCode.END){
        navController.navigate(WatchNavItem.BattleEnd.route) {
            popUpTo(0)
            launchSingleTop =true
        }
        battleViewModel.battleEnd()
    }

    val bg = painterResource(R.drawable.battle_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    BattleBackgroundGif()




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

                    ButtonSoundPlay()
                    battleViewModel.select(MessageType.LEFT)

//                    selectState.value = "LEFT"
//                    navController.navigate(WatchNavItem.BattleActive.route){
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop =true
//                    }
//                    viewModel.isSelectEnd = true
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
                    ButtonSoundPlay()
                    battleViewModel.select(MessageType.RIGHT)
//                    selectState.value = "RIGHT"
//                    navController.navigate(WatchNavItem.BattleActive.route){
//                        popUpTo(navController.graph.findStartDestination().id)
//                        launchSingleTop =true
//                    }
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

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleSelectPreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleSelect(navController, viewModel)
    }
}