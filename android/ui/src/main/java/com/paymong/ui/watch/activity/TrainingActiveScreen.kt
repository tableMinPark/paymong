package com.paymong.ui.watch.activity

import android.media.SoundPool
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.SystemClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.R
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.TrainingViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.domain.watch.main.MainViewModel


@Composable
fun TrainingActive(
    navController: NavHostController,
    traingviewModel: TrainingViewModel
) {
    traingviewModel.trainingInit()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    // Background
    val img = painterResource(R.drawable.training_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    TrainingBackgroundGif()

    if (screenWidthDp < 200) {
        SmallWatch(navController, traingviewModel)
    }
    else {
        BigWatch(navController, traingviewModel)
    }

    // GIF
    if (traingviewModel.isTrainingEnd) {
        null
    } else {
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

@Composable
fun SmallWatch (
    navController: NavHostController,
    traingviewModel: TrainingViewModel
) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().clickable {
                traingviewModel.screenClick() {
                    navController.navigate(WatchNavItem.Activity.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
        ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = String.format(
                            "%02d:%02d",
                            traingviewModel.second,
                            traingviewModel.nanoSecond / 10000000
                        ),
                        fontFamily = dalmoori,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)

                ) {
                    Text(
                        text = String.format("%d", traingviewModel.count),
                        fontFamily = dalmoori,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

            // Character

            val mainviewModel: MainViewModel = viewModel()
            val chCode = mainviewModel.mong.mongCode
            val chA = painterResource(chCode.resourceCode)

            if (traingviewModel.isTrainingEnd) {
                if (traingviewModel.count >= 50) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(60.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.success),
                            contentDescription = "success",
                            modifier = Modifier.width(160.dp).height(80.dp).padding(bottom=7.dp)
                        )
                    }
                    WinSoundPlay()
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(60.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fail),
                            contentDescription = "fail",
                            modifier = Modifier.width(100.dp).height(80.dp).padding(bottom=5.dp)

                        )
                    }
                    LoseSoundPlay()
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = chA,
                        contentDescription = null,
                        modifier = Modifier.width(80.dp).height(80.dp)
                    )
                }
            }
            if (traingviewModel.isTrainingEnd) {
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
                            traingviewModel.screenClick() {
                                navController.navigate(WatchNavItem.Activity.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                )
                Text(
                    text = "종료",
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    color = Color(0xFF0C4DA2)
                ) }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(10.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "터치해서 훈련하기",
                        fontFamily = dalmoori,
                        fontSize = 9.sp
                    )
                }
            }
    }
}

@Composable
fun BigWatch (
    navController: NavHostController,
    traingviewModel: TrainingViewModel
) {
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
        while ( soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 0.5f) == 0 && waitCounter < waitLimit){
            waitCounter++
            SystemClock.sleep(throttle.toLong())
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().clickable {
            traingviewModel.screenClick() {
                navController.navigate(WatchNavItem.Activity.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(
                text = String.format(
                    "%02d:%02d",
                    traingviewModel.second,
                    traingviewModel.nanoSecond / 10000000
                ),
                fontFamily = dalmoori,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(
                text = String.format("%d", traingviewModel.count),
                fontFamily = dalmoori,
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        // Character

        val mainviewModel: MainViewModel = viewModel()
        val chCode = mainviewModel.mong.mongCode
        val chA = painterResource(chCode.resourceCode)

        if (traingviewModel.isTrainingEnd) {
            if (traingviewModel.count >= 50) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(90.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.success),
                        contentDescription = "success",
                        modifier = Modifier.width(180.dp).height(100.dp).padding(bottom=12.dp)
                    )
                }
                WinSoundPlay()
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(90.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fail),
                        contentDescription = "fail",
                        modifier = Modifier.width(120.dp).height(100.dp).padding(bottom=10.dp)

                    )
                }
                LoseSoundPlay()
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = chA,
                    contentDescription = null,
                    modifier = Modifier.width(100.dp).height(100.dp)
                )
            }
        }
        if (traingviewModel.isTrainingEnd) {
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
                            traingviewModel.screenClick() {
                                navController.navigate(WatchNavItem.Activity.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                )
                Text(
                    text = "종료",
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    color = Color(0xFF0C4DA2)
                ) }
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
                    fontSize = 11.sp
                )
            }
        }
    }
}


@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun TrainingPreview() {
    val navController = rememberSwipeDismissableNavController()
    val traingviewModel: TrainingViewModel = viewModel()
    PaymongTheme {
        TrainingActive(navController, traingviewModel)
    }
}