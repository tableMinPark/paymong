package com.paymong.ui.watch.activity

import android.graphics.Insets.add
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
//import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageRequest
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.TrainingViewModel
import com.paymong.domain.watch.battle.BattleFindViewModel
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize







@Composable
fun TrainingActive(
    navController: NavHostController
) {
    val viewModel: TrainingViewModel = viewModel()
    TrainingActiveUI(navController, viewModel)
}




@Composable
fun TrainingActiveUI(
    navController: NavHostController,
    viewModel: TrainingViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    println(screenWidthDp)


    // Background
    val img = painterResource(R.drawable.training_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)


    if (screenWidthDp < 200) {
        SmallWatch(   navController, viewModel)
    }
    else {
        BigWatch( navController, viewModel)

    }


    // GIF
    if (viewModel.isTrainingEnd) {
        null
    } else {
        LoadingGif()
    }

}



@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun TrainingPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        TrainingActive(navController)
    }
}


@ExperimentalCoilApi
@Composable
fun LoadingGif(
    modifier: Modifier = Modifier
) {
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
//            .padding(top = 10.dp)
    )
}

@Composable
fun SmallWatch (
    navController: NavHostController,
    viewModel: TrainingViewModel
) {
    // Small Watch

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().clickable {
                viewModel.screenClick() {
                    navController.navigate(WatchNavItem.Activity.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            }
        ) {
            val TraingviewModel: TrainingViewModel = viewModel()

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = String.format(
                            "%02d:%02d",
                            viewModel.second,
                            viewModel.nanoSecond / 10000000
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
                        text = String.format("%d", viewModel.count),
                        fontFamily = dalmoori,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

            // Character

            val viewModel: MainInfoViewModel = viewModel()
            var findCode = viewModel.characterCode
            var chCode = CharacterCode.valueOf(findCode)
            val chA = painterResource(chCode.code)


            if (TraingviewModel.isTrainingEnd) {
                if (TraingviewModel.count >= 1) {
                    Box(

                        modifier = Modifier.fillMaxWidth()
                            .height(60.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
//                        Image(
//                            painter = chA,
//                            contentDescription = null,
//                            modifier = Modifier.width(80.dp).height(80.dp)
//                        )
                        Image(
                            painter = painterResource(id = R.drawable.success),
                            contentDescription = "success",
                            modifier = Modifier.width(160.dp).height(80.dp).padding(bottom=7.dp)
                        )
                    }
                } else {
                    Box(

                        modifier = Modifier.fillMaxWidth()
                            .height(60.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
//                        Image(
//                            painter = chA,
//                            contentDescription = null,
//                            modifier = Modifier.width(80.dp).height(80.dp)
//                        )
                        Image(
                            painter = painterResource(id = R.drawable.fail),
                            contentDescription = "fail",
                            modifier = Modifier.width(100.dp).height(80.dp).padding(bottom=5.dp)

                        )
                    }
                }
//                    Box(Modifier.size(80.dp))
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



                if (TraingviewModel.isTrainingEnd) {
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
                                TraingviewModel.screenClick() {
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
    viewModel: TrainingViewModel
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().clickable {
            viewModel.screenClick() {
                navController.navigate(WatchNavItem.Activity.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    ) {
        val TraingviewModel: TrainingViewModel = viewModel()

        Box(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(
                text = String.format(
                    "%02d:%02d",
                    viewModel.second,
                    viewModel.nanoSecond / 10000000
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
                text = String.format("%d", viewModel.count),
                fontFamily = dalmoori,
                fontSize = 25.sp
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        // Character

        val viewModel: MainInfoViewModel = viewModel()
        var findCode = viewModel.characterCode
        var chCode = CharacterCode.valueOf(findCode)
        val chA = painterResource(chCode.code)


        if (TraingviewModel.isTrainingEnd) {
            if (TraingviewModel.count >= 1) {
                Box(

                    modifier = Modifier.fillMaxWidth()
                        .height(90.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
//                        Image(
//                            painter = chA,
//                            contentDescription = null,
//                            modifier = Modifier.width(80.dp).height(80.dp)
//                        )
                    Image(
                        painter = painterResource(id = R.drawable.success),
                        contentDescription = "success",
                        modifier = Modifier.width(180.dp).height(100.dp).padding(bottom=12.dp)
                    )
                }
            } else {
                Box(

                    modifier = Modifier.fillMaxWidth()
                        .height(90.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
//                        Image(
//                            painter = chA,
//                            contentDescription = null,
//                            modifier = Modifier.width(80.dp).height(80.dp)
//                        )
                    Image(
                        painter = painterResource(id = R.drawable.fail),
                        contentDescription = "fail",
                        modifier = Modifier.width(120.dp).height(100.dp).padding(bottom=10.dp)

                    )
                }
            }
//                    Box(Modifier.size(80.dp))
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



        if (TraingviewModel.isTrainingEnd) {
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
                            TraingviewModel.screenClick() {
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
