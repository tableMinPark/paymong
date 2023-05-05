package com.paymong.ui.watch.battle

import android.media.SoundPool
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.landing.MainBackgroundGif

@Composable
fun BattleLanding(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {
    val bg = painterResource(R.drawable.main_bg)
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = 0
    var battleImgSize = 0

    if (screenWidthDp < 200) {
        fontSize = 12
        battleImgSize = 150

    }
    else {
        fontSize = 15
        battleImgSize = 180
    }



    val soundPool = SoundPool.Builder()
        .setMaxStreams(1) // 동시에 재생 가능한 스트림의 최대 수
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)


    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }


    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()
    Column(
        verticalArrangement = Arrangement.Center,

        modifier = Modifier
            .fillMaxHeight()
            .clickable {
                ButtonSoundPlay()
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

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattlePreview() {
    val navController = rememberSwipeDismissableNavController()
    val battleViewModel: BattleViewModel = viewModel()

    PaymongTheme {
        BattleLanding(navController, battleViewModel)
    }
}

