package com.paymong.ui.watch.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.paymong.common.code.MongCode
import com.paymong.common.code.SoundCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.TrainingViewModel
import com.paymong.ui.theme.dalmoori

import com.paymong.domain.SoundViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.LoadingGif

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrainingActive(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    trainingViewModel: TrainingViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val successPadding = if (screenWidthDp < 200) 7 else 12
    val successWidth = if (screenWidthDp < 200) 160 else 180
    val successHeight = if (screenWidthDp < 200) 80 else 100
    val failPadding = if (screenWidthDp < 200) 5 else 10
    val failWidth = if (screenWidthDp < 200) 100 else 120
    val failHeight = if (screenWidthDp < 200) 180 else 100
    val characterSize = if (screenWidthDp < 200) 80 else 100
    val exitFontSize = if (screenWidthDp < 200) 11 else 13
    val infoFontSize = if (screenWidthDp < 200) 9 else 11

    Background(watchViewModel.mapCode, true)
    if (!trainingViewModel.isTrainingEnd) LoadingGif()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .clickable {
                if(!trainingViewModel.isTrainingEnd) {
                    trainingViewModel.addCount()
                }
            }
    ) {
        TrainingTime(trainingViewModel)
        Spacer(modifier = Modifier.height(3.dp))

        val mongCode = watchViewModel.mong.mongCode
        val mongResourceCode = painterResource(mongCode.resourceCode)

        // 훈련 끝
        if (trainingViewModel.isTrainingEnd) {
            trainingViewModel.trainingEnd(watchViewModel)

            TrainingEnd(successHeight, successWidth, successHeight, successPadding, soundViewModel, failHeight, failWidth, failPadding, trainingViewModel)
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
                            soundViewModel.soundPlay(SoundCode.TRAINING_BUTTON)
                            trainingViewModel.isTrainingEnd = false
                            watchViewModel.point -= 50
                            navController.navigate(WatchNavItem.Activity.route) {
                                popUpTo(navController.graph.findStartDestination().id)
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
                    fontSize = exitFontSize.sp,
                    color = Color(0xFF0C4DA2)
                )
            }
        }
        // 훈련 진행 중
        else
            Training(mongCode, mongResourceCode, characterSize, infoFontSize)
    }
}


@Composable
fun TrainingEnd(
    boxHeight: Int,
    successWidth: Int,
    successHeight: Int,
    successPadding: Int,
    soundViewModel : SoundViewModel,
    failHeight: Int,
    failWidth: Int,
    failPadding: Int,
    trainingViewModel : TrainingViewModel,
) {
    if (trainingViewModel.count >= 50) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(boxHeight.dp)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.success),
                contentDescription = "success",
                modifier = Modifier
                    .width(successWidth.dp)
                    .height(successHeight.dp)
                    .padding(bottom = successPadding.dp)
            )
        }
        soundViewModel.soundPlay(SoundCode.TRAINING_WIN)
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(boxHeight.dp)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fail),
                contentDescription = "fail",
                modifier = Modifier
                    .width(failWidth.dp)
                    .height(failHeight.dp)
                    .padding(bottom = failPadding.dp)

            )
        }
        soundViewModel.soundPlay(SoundCode.TRAINING_LOSE)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Training(
    mongCode: MongCode,
    mongResourceCode: Painter,
    characterSize: Int,
    infoFontSize: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        if ( mongCode.code == "CH444") {
            Box(
                modifier = Modifier
                    .width(characterSize.dp)
                    .height(characterSize.dp)
            ) {
                LoadingGif()
            }
        } else {
            Image(
                painter = mongResourceCode,
                contentDescription = null,
                modifier = Modifier
                    .width(characterSize.dp)
                    .height(characterSize.dp)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(20.dp)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(
            text = "터치해서 훈련하기",
            fontFamily = dalmoori,
            fontSize = infoFontSize.sp
        )
    }
}

@Composable
// 타이머
fun TrainingTime(
    trainingViewModel: TrainingViewModel
){
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val timeFontSize = if (screenWidthDp < 200) 16 else 20
    val countFontSize = if (screenWidthDp < 200) 20 else 25

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(
            text = String.format(
                "%02d:%02d",
                trainingViewModel.second,
                trainingViewModel.nanoSecond / 10000000
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
        Text(
            text = String.format("%d", trainingViewModel.count),
            fontFamily = dalmoori,
            fontSize = countFontSize.sp
        )
    }
}