package com.paymong.ui.watch.main

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.MongStateCode
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.app.main.CreateImageList
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.CharacterGif
import com.paymong.ui.watch.common.EmotionGif
import com.paymong.ui.watch.common.LoadingGif

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainInfo(
    animationState: MutableState<AnimationCode>,
    mainViewModel : WatchViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val characterSize = if (screenWidthDp < 200) 110 else 120
    val poopSize = if (screenWidthDp < 200) 25 else 35
    val boxTopPadding = if (screenWidthDp < 200) 65 else 75
    val mongResourceCode = mainViewModel.mong.mongCode.resourceCode

    if(mainViewModel.isHappy) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
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
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
                .padding(top = boxTopPadding.dp) ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()

            ) {
                if (mainViewModel.mong.mongCode.code == "CH444") {
                    Text(
                        text = "스마트폰에서\n알을 생성해주세요.",
                        textAlign = TextAlign.Center,
                        lineHeight = 50.sp,
                        fontFamily = dalmoori,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                } else if (mainViewModel.stateCode == MongStateCode.CD005 ) { // 죽음
                    Box(contentAlignment = Alignment.Center,) {
                        Image(painter = painterResource(R.drawable.rip),
                            contentDescription = null,
                            modifier = Modifier
                                .size(characterSize.dp))
                    }
                } else if (mainViewModel.stateCode == MongStateCode.CD006) { // 졸업
                    Box(contentAlignment = Alignment.Center,) {
                        Image(painter = painterResource(R.drawable.rip), // 졸업 이미지 넣기
                            contentDescription = null,
                            modifier = Modifier
                                .size(characterSize.dp))
                    }
                } else {
                    Box(contentAlignment = Alignment.Center,) {
                        if (mainViewModel.stateCode == MongStateCode.CD007) { //진화대기
                            Text(
                                text = "성장을 위해\n화면을 터치해주세요.",
                                textAlign = TextAlign.Center,
                                lineHeight = 50.sp,
                                fontFamily = dalmoori,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.clickable {
                                    mainViewModel.evolution()
                                    mainViewModel.evolutionisClick = true
                                }
                            )
                        } else {
                            if (mainViewModel.evolutionisClick) {
                                Image(painter = painterResource(mainViewModel.undomong.resourceCode),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(characterSize.dp))

                                Box(modifier = Modifier.scale(2f)) {
                                    CreateImageList()
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    mainViewModel.evolutionisClick = false
                                }, 1800)
                            } else {
                                CharacterGif(mainViewModel)
                                val code = mainViewModel.mong.mongCode.code.split("CH")[1].toInt()
                                if(code / 100 == 1){
                                    EmotionGif(mainViewModel, 0, 0, 0, 40, mainViewModel.stateCode)
                                } else if(code / 100 == 2){
                                    val end = code % 10
                                    Log.d("end",end.toString())
                                    if(end == 1){ //2_1
                                        EmotionGif(mainViewModel, 0, 25, 40, 40, mainViewModel.stateCode)
                                    } else { //2_0, 2_2
                                        EmotionGif(mainViewModel, 0, 0, 50, 40, mainViewModel.stateCode)
                                    }
                                } else if(code / 100 == 3){
                                    val end = code % 10
                                    if(end == 1){ //3_1
                                        EmotionGif(mainViewModel, 0, 50, 40, 40, mainViewModel.stateCode)
                                    } else{ //3_0, 3_2
                                        EmotionGif(mainViewModel, 0, 0, 35  , 40, mainViewModel.stateCode)
                                    }
                                }
                            }
                        }
                    }
                }}

                // 똥
                when (mainViewModel.poopCount) {
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