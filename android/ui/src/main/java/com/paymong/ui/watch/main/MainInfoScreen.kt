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
import androidx.compose.ui.unit.dp
import com.paymong.common.R
import com.paymong.common.code.AnimationCode
import com.paymong.domain.watch.WatchViewModel

@Composable
fun MainInfo(
    animationState: MutableState<AnimationCode>,
    mainViewModel : WatchViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val characterSize = if (screenWidthDp == 200) 100 else 120
    val poopSize = if (screenWidthDp == 200) 120 else 35
    val mongResourceCode = mainViewModel.mong.mongCode.resourceCode

    if(mainViewModel.isHappy) {
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
                Image(
                    painter = painterResource(mongResourceCode),
                    contentDescription = null,
                    modifier = Modifier
                        .width(characterSize.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ){
                            mainViewModel.stroke()
                            Handler(Looper.getMainLooper()).postDelayed({
                                mainViewModel.isHappy = false
                            }, 2000)
                        }
                )
            }

            // 똥
            when(mainViewModel.poopCount) {
                1 -> Poops(0, 100, 75, 0, poopSize)
                2 -> {
                    Poops(0, 100, 75, 0, poopSize)
                    Poops(90, 0, 80, 0, poopSize)
                }
                3 -> {
                    Poops(0, 100, 75, 0, poopSize)
                    Poops(90, 0, 80, 0, poopSize)
                    Poops(30, 0, 92, 0, poopSize)
                }
                4 -> {
                    Poops(0, 100, 75, 0, poopSize)
                    Poops(90, 0, 80, 0, poopSize)
                    Poops(30, 0, 92, 0, poopSize)
                    Poops(0, 60, 95, 0, poopSize)
                }
            }
        }
    }
}

@Composable
fun Poops(start: Int, end: Int, top: Int, bottom: Int, poopSize: Int){
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