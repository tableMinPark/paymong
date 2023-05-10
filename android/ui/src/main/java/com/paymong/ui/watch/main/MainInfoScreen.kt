package com.paymong.ui.watch.main

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymong.common.R
import com.paymong.common.code.AnimationCode
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun MainInfo(
    animationState: MutableState<AnimationCode>,
    mainviewModel : WatchViewModel
) {
    MainInfoUI(animationState, mainviewModel)
}

@Composable
fun MainInfoUI(
    animationState: MutableState<AnimationCode>,
    mainviewModel : WatchViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var characterSize = 0
    var poopSize = 0



    if (screenWidthDp < 200) {
        characterSize = 100
        poopSize = 25

    }
    else {
        characterSize = 120
        poopSize = 35
    }

    if(mainviewModel.isHappy) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painterResource(R.drawable.heart), contentDescription = null)
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .padding(top = 60.dp) ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val character = painterResource(mainviewModel.mong.mongCode.resourceCode)
                Image(
                    painter = character,
                    contentDescription = null,
                    modifier = Modifier
                        .width(characterSize.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ){
                            mainviewModel.stroke()
                            Handler(Looper.getMainLooper()).postDelayed({
                                mainviewModel.isHappy = false
                            }, 2000)
                        }
                )
            }

            val poopCount = mainviewModel.poopCount

            if (poopCount == 1) {
                Poops(0, 100, 75, 0, poopSize)
            }
            else if (poopCount == 2) {
                Poops(0, 100, 75, 0, poopSize)
                Poops(90, 0, 80, 0, poopSize)
            }
            else if (poopCount == 3) {
                Poops(0, 100, 75, 0, poopSize)
                Poops(90, 0, 80, 0, poopSize)
                Poops(30, 0, 92, 0, poopSize)
            }
            else if (poopCount == 4 ) {
                Poops(0, 100, 75, 0, poopSize)
                Poops(90, 0, 80, 0, poopSize)
                Poops(30, 0, 92, 0, poopSize)
                Poops(0, 60, 95, 0, poopSize)
            }
        }
    }
}

@Composable
fun Poops(start:Int, end:Int, top:Int, bottom:Int, poopSize:Int ){
    val poops = painterResource(R.drawable.poops)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = start.dp, end = end.dp, top = top.dp, bottom = bottom.dp)
    ) {
        Image(
            painter = poops,
            contentDescription = null,
            modifier = Modifier.size(poopSize.dp)
        )
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainInfoPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val mainviewModel : WatchViewModel = viewModel()
    PaymongTheme {
        MainInfo(animationState, mainviewModel)
    }
}