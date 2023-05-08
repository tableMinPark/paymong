package com.paymong.ui.watch.battle

import android.media.SoundPool
import android.os.SystemClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MatchingCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.domain.watch.main.MainViewModel
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


    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()
    val context = LocalContext.current
    val winSound = soundPool.load(context, com.paymong.ui.R.raw.win_sound, 1)
    val loseSound = soundPool.load(context, com.paymong.ui.R.raw.lose_sound, 1)
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)

    fun WinSoundPlay () {


        val waitLimit = 1000
        var waitCounter = 0
        var throttle = 10
        while ( soundPool.play(winSound, 0.5f, 0.5f, 1, 0, 0.5f) == 0 && waitCounter < waitLimit){
            waitCounter++
            SystemClock.sleep(throttle.toLong())
        }
    }
    fun LoseSoundPlay () {


        val waitLimit = 1000
        var waitCounter = 0
        var throttle = 10
        while ( soundPool.play(loseSound, 0.5f, 0.5f, 1, 0, 0.5f) == 0 && waitCounter < waitLimit){
            waitCounter++
            SystemClock.sleep(throttle.toLong())
        }
    }

    fun ButtonSoundPlay () {

        val waitLimit = 1000
        var waitCounter = 0
        var throttle = 10
        while ( soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1f) == 0 && waitCounter < waitLimit){
            waitCounter++
            SystemClock.sleep(throttle.toLong())
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
                WinSoundPlay()
                Image(painter = win, contentDescription = null, modifier = Modifier.width(150.dp))
            } else {
                val lose = painterResource(R.drawable.lose)
                LoseSoundPlay()
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
                        ButtonSoundPlay()
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