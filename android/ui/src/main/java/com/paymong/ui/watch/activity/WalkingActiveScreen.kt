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
import com.paymong.domain.watch.main.MainViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun WalkingActive(
    navController: NavHostController,
    walkingViewModel: WalkingViewModel
) {
//    viewModel.setSensor(LocalContext.current)
    walkingViewModel.walkingInit()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val img = painterResource(R.drawable.walking_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    WalkingBackgroundGif()

    if (screenWidthDp < 200) {
        WalkingSmallWatch(navController, walkingViewModel)
    }
    else {
        WalkingBigWatch(navController, walkingViewModel)
    }
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

@Composable
fun WalkingSmallWatch (
    navController: NavHostController,
    walkingViewModel: WalkingViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier.height(10.dp))
        // 00:00
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
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        // 0 걸음
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
                    .padding(start = 25.dp)
            ) {
                Text(
                    text = String.format("%d", walkingViewModel.walkCount),
                    fontFamily = dalmoori,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Bottom)
                )
                Text(
                    text = " 걸음",
                    fontFamily = dalmoori,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
        }

        if (walkingViewModel.isWalkingEnd) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(85.dp)
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = "?산책 종료?",
                    modifier = Modifier,
                    fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
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
                val mainviewModel: MainViewModel = viewModel()
                val chCode = mainviewModel.mong.mongCode
                val chA = painterResource(chCode.resourceCode)
                Image(
                    painter = chA, contentDescription = null, modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                )
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
            Box() {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(20.dp)
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
                            fontSize = 12.sp,
                            color = Color(0xFF0C4DA2)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(20.dp)
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
                            fontSize = 12.sp,
                            color = Color(0xFF0C4DA2)
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(20.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ){
                Image(
                    painter = painterResource(id = R.drawable.blue_bnt),
                    contentDescription = "blue_bnt",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clickable {
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
                    fontSize = 10.sp,
                    color = Color(0xFF0C4DA2)
                )
            }
        }
    }
}


@Composable
fun WalkingBigWatch (
    navController: NavHostController,
    walkingViewModel: WalkingViewModel
){
    Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxHeight()
) {
        Box(modifier = Modifier.height(10.dp))
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
                fontSize = 20.sp
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
                    .padding(start = 25.dp)
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
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
        }

        if (walkingViewModel.isWalkingEnd) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(top = 30.dp)
            ) {
                Text(
                    text = "?산책 종료?",
                    modifier = Modifier,
                    fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
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
                val mainviewModel: MainViewModel = viewModel()
                val chCode = mainviewModel.mong.mongCode
                val chA = painterResource(chCode.resourceCode)
                Image(
                    painter = chA, contentDescription = null, modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )
            }
        }

        if (walkingViewModel.isWalkingEnd) {
            if (walkingViewModel.realWalkingEnd) {
                navController.navigate(WatchNavItem.Activity.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
            Box(modifier = Modifier.padding(top = 5.dp)) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 60.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
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
                            fontSize = 14.sp,
                            color = Color(0xFF0C4DA2)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 60.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
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
                            fontSize = 14.sp,
                            color = Color(0xFF0C4DA2)
                        )
                    }
                }

            }
        } else {
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
                    fontSize = 14.sp,
                    color = Color(0xFF0C4DA2)
                )
            }
        }
    }
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