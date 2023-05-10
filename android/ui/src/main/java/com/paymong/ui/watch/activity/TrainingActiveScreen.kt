package com.paymong.ui.watch.activity

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import com.paymong.common.R
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.TrainingViewModel
import com.paymong.ui.theme.dalmoori

import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.domain.watch.WatchViewModel

@Composable
fun TrainingTime(trainingViewModel: TrainingViewModel){
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    var timeFontSize = 20
    var countFontSize = 25

    if (screenWidthDp < 200) {
        timeFontSize = 16
        countFontSize = 20
    }


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

@Composable
fun TrainingActive(
    navController: NavHostController,
    trainingViewModel: TrainingViewModel,
    watchViewModel: WatchViewModel = viewModel()
) {
    LaunchedEffect(key1 = 0){
        trainingViewModel.trainingInit()
    }
    // Ui Config
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    var boxHeight = 90
    var successPadding = 12
    var successWidth = 180
    var successHeight = 100

    var failPadding = 10
    var failWidth = 120
    var failHeight = 100
    var characterSize = 100
    var exitFontSize = 13
    var infoFontSize = 11


    if (screenWidthDp < 200) {
        boxHeight = 60
        successPadding = 7
        successWidth = 160
        successHeight = 80

        failPadding = 5
        failWidth = 100
        failHeight = 180
        characterSize = 80
        exitFontSize = 11
        infoFontSize = 9
    }

    // Background
    var img = painterResource(R.drawable.training_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    TrainingBackgroundGif()

    // Character
    val chCode = watchViewModel.mong.mongCode
    val chA = painterResource(chCode.resourceCode)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .clickable {
                trainingViewModel.screenClick() {
                    navController.navigate(WatchNavItem.Activity.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
    ) {
        TrainingTime(trainingViewModel)
        Spacer(modifier = Modifier.height(3.dp))

        if (trainingViewModel.isTrainingEnd) {
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
                buttonSoundPlay(trainingViewModel, "Win")

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
                buttonSoundPlay(trainingViewModel, "Lose")
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                if ( chCode.code == "CH444") {
                    Box(
                        modifier = Modifier
                            .width(characterSize.dp)
                            .height(characterSize.dp)
                    ) {
                        LoadingGif()
                    }
                } else {
                    Image(
                        painter = chA,
                        contentDescription = null,
                        modifier = Modifier
                            .width(characterSize.dp)
                            .height(characterSize.dp)
                    )
                }
            }
        }
        if (trainingViewModel.isTrainingEnd) {
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
                            buttonSoundPlay(trainingViewModel, "Bnt")
                            trainingViewModel.screenClick() {
                                navController.navigate(WatchNavItem.Activity.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
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
        } else {
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
    }

    // GIF
    if (!trainingViewModel.isTrainingEnd) {
        LoadingGif()
    }
}

@ExperimentalCoilApi
@Composable
fun LoadingGif() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder(context))
            } else {
                add(GifDecoder())
            }
        }
        .build()
    Image(
        painter = rememberImagePainter(
            imageLoader = imageLoader,
            data = R.drawable.loading,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
    )
}

@ExperimentalCoilApi
@Composable
fun TrainingBackgroundGif() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder(context))
            } else {
                add(GifDecoder())
            }
        }
        .build()
    Image(
        painter = rememberImagePainter(
            imageLoader = imageLoader,
            data = R.drawable.training_bg_gif,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
    )
}

fun buttonSoundPlay (trainingViewModel : TrainingViewModel, soundName : String) {
    if (soundName == "Bnt") {
        trainingViewModel.soundPool.play(trainingViewModel.buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
    else if (soundName == "Win") {
        trainingViewModel.soundPool.play(trainingViewModel.winSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
    else if (soundName == "Lose") {
        trainingViewModel.soundPool.play(trainingViewModel.loseSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }

}
