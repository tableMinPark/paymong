package com.paymong.ui.watch.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.SoundCode
import com.paymong.common.code.WalkingCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.WalkingViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.LoadingGif
import com.paymong.ui.watch.common.WalkingBackgroundGif


@OptIn(ExperimentalCoilApi::class)
@Composable
fun WalkingActive(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    walkingViewModel: WalkingViewModel = viewModel()
) {
    Background(false)
    WalkingBackgroundGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val rowHeight = if (screenWidthDp < 200) 85 else 100
    val rowPadding = if (screenWidthDp < 200) 25 else 40
    val questionFontSize = if (screenWidthDp < 200) 15 else 18
    val characterSize = if (screenWidthDp < 200) 80 else 100
    val quitBntHeight = if (screenWidthDp < 200) 20 else 30
    val quitBntFontSize = if (screenWidthDp < 200) 10 else 11
    val yesNoBntPadding = if (screenWidthDp < 200) 40 else 60
    val yesNoFontSize = if (screenWidthDp < 200) 12 else 14
    val yesNoBntWidth = if (screenWidthDp < 200) 60 else 50
    val yesNoBntHeight = if (screenWidthDp < 200) 20 else 40

    // 끝남
    if (walkingViewModel.walkingState == WalkingCode.WALKING_END) {
        navController.navigate(WatchNavItem.Activity.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier.height(10.dp))

        WalkingTime(walkingViewModel)

        // 산책 중
        if (walkingViewModel.walkingState == WalkingCode.PAUSE) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(rowHeight.dp)
                    .padding(top = rowPadding.dp)
            ) {
                Text(
                    text = "?산책 종료?",
                    modifier = Modifier,
                    fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                    fontSize = questionFontSize.sp,
                    color = Color(0xFFffffff)
                )
            }
        }
        // 산책 중 아닐 때
        else {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                val mongCode = watchViewModel.mong.mongCode
                val mongResourceCode = painterResource(mongCode.resourceCode)
                if ( mongCode.code == "CH444") {
                    Box(
                        modifier = Modifier
                            .width(characterSize.dp)
                            .height(characterSize.dp)
                    ){
                        LoadingGif()
                    }
                } else {
                    Image(
                        painter = mongResourceCode, contentDescription = null, modifier = Modifier
                            .width(characterSize.dp)
                            .height(characterSize.dp)
                    )
                }
            }
        }

        if (walkingViewModel.walkingState == WalkingCode.PAUSE) {
            Box(modifier = Modifier.padding(bottom =0.dp)) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = yesNoBntPadding.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(yesNoBntWidth.dp)
                            .height(yesNoBntHeight.dp)
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
                                    // 산책 끝
                                    soundViewModel.soundPlay(SoundCode.WALKING_BUTTON)
                                    walkingViewModel.walkingEnd()
                                }
                        )
                        Text(
                            text = "YES",
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .wrapContentHeight(Alignment.CenterVertically)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            fontFamily = dalmoori,
                            textAlign = TextAlign.Center,
                            fontSize = yesNoFontSize.sp,
                            color = Color(0xFF0C4DA2)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = yesNoBntPadding.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(yesNoBntWidth.dp)
                            .height(yesNoBntHeight.dp)
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
                                    soundViewModel.soundPlay(SoundCode.WALKING_BUTTON)
                                    walkingViewModel.walkingState = WalkingCode.WALKING
                                }
                        )
                        Text(
                            text = "NO",
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .wrapContentHeight(Alignment.CenterVertically)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            fontFamily = dalmoori,
                            textAlign = TextAlign.Center,
                            fontSize = yesNoFontSize.sp,
                            color = Color(0xFF0C4DA2)
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(quitBntHeight.dp)
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
                            soundViewModel.soundPlay(SoundCode.WALKING_BUTTON)
                            walkingViewModel.walkingState = WalkingCode.PAUSE
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
                    fontSize = quitBntFontSize.sp,
                    color = Color(0xFF0C4DA2)
                )
            }
        }
    }
}

@Composable
fun WalkingTime (
    walkingViewModel: WalkingViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val timeFontSize = if (screenWidthDp < 200) 16 else 20
    val walkingCountFontSize = if (screenWidthDp < 200) 20 else 25
    val walkingCountInfoFontSize = if (screenWidthDp < 200) 12 else 17

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(
            text = String.format(
                "%02d:%02d",
                walkingViewModel.walkMinute,
                walkingViewModel.walkSecond
            ),
            fontFamily = dalmoori,
            fontSize = timeFontSize.sp
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = walkingCountFontSize.dp)
        ) {
            Text(
                text = String.format("%d", walkingViewModel.walkCount),
                fontFamily = dalmoori,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Text(
                text = " 걸음",
                fontFamily = dalmoori,
                fontSize = walkingCountInfoFontSize.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
    }
}

