package com.paymong.ui.watch.activity

import android.os.Build
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
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.R
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.WalkingViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun WalkingTime (walkingViewModel:WalkingViewModel) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    var timeFontSize = 20
    var walkingCountFontSize = 25
    var walkingCountInfoFontSize = 17

    if (screenWidthDp < 200) {
        timeFontSize = 16
        walkingCountFontSize = 20
        walkingCountInfoFontSize = 12
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

@Composable
fun WalkingActive(
    navController: NavHostController,
    walkingViewModel: WalkingViewModel
) {
//    viewModel.setSensor(LocalContext.current)
    walkingViewModel.walkingInit()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var rowHeight = 100
    var rowPadding = 40
    var questionFontSize = 18
    var characterSize = 100
    var quitBntHeight = 30
    var quitBntFontSize = 11
    var yesNoBntPadding = 60
    var yesNoFontSize = 14
    var yesNoBntWidth = 50
    var yesNoBntHeight = 40

    if (screenWidthDp < 200) {
        rowHeight = 85
        rowPadding = 25
        questionFontSize = 15
        characterSize  = 80
        quitBntHeight = 20
        quitBntFontSize = 10
        yesNoBntPadding = 40
        yesNoFontSize = 12
        yesNoBntWidth = 60
        yesNoBntHeight = 20
    }

    val img = painterResource(R.drawable.walking_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    WalkingBackgroundGif()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier.height(10.dp))

        WalkingTime(walkingViewModel)
        if (walkingViewModel.isWalkingEnd) {
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
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                val mainviewModel: WatchViewModel = viewModel()
                val chCode = mainviewModel.mong.mongCode
                val chA = painterResource(chCode.resourceCode)
                if ( chCode.code == "CH444") {
                    Box(
                        modifier = Modifier
                            .width(characterSize.dp)
                            .height(characterSize.dp)
                    ){
                        LoadingGif()}
                } else {
                    Image(
                        painter = chA, contentDescription = null, modifier = Modifier
                            .width(characterSize.dp)
                            .height(characterSize.dp)
                    )
                }
            }
        }

        if (walkingViewModel.isWalkingEnd) {
            if (walkingViewModel.realWalkingEnd) {
                walkingViewModel.isWalkingEnd = false
                walkingViewModel.realWalkingEnd = false
                navController.navigate(WatchNavItem.Activity.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
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
                                    ButtonSoundPlay(walkingViewModel)
                                    walkingViewModel.walkingEnd()
                                    walkingViewModel.realWalkingEnd = true
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
                                    ButtonSoundPlay(walkingViewModel)
                                    walkingViewModel.isWalkingEnd = false
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
                            ButtonSoundPlay(walkingViewModel)
                            walkingViewModel.isWalkingEnd = true
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
fun buttonSoundPlay (walkingViewModel: WalkingViewModel) {
    walkingViewModel.soundPool.play(walkingViewModel.buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
}
@ExperimentalCoilApi
@Composable
fun WalkingBackgroundGif() {
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
            data = R.drawable.walking_bg_gif,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
    )
}





@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun WalkingPreview() {
    val navController = rememberSwipeDismissableNavController()
    val walkingViewModel: WalkingViewModel = viewModel()
    PaymongTheme {
        WalkingActive(navController, walkingViewModel)
    }
}