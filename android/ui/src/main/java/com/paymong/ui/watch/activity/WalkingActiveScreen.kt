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
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.WalkingViewModel
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun WalkingActive(navController: NavHostController) {
    val viewModel: WalkingViewModel = viewModel()
//    viewModel.setSensor(LocalContext.current)
    WalkingActiveUI(navController, viewModel)
}

@Composable
fun WalkingActiveUI(
    navController: NavHostController,
    viewModel: WalkingViewModel
) {


    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    val img = painterResource(R.drawable.walking_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    WalkingBackgroundGif()

    if (screenWidthDp < 200) {
        SmallWatch( navController, viewModel)
    }
    else {
        BigWatch( navController, viewModel)

    }


}


@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun WalkingPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        WalkingActive(navController)
    }
}


@ExperimentalCoilApi
@Composable
fun WalkingBackgroundGif(

) {
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
//            .padding(top = 10.dp)
    )
}



@Composable
fun SmallWatch (  navController: NavHostController,
                  viewModel: WalkingViewModel)
{ Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxHeight()

) {

    Box(
        modifier = Modifier.height(10.dp) )
    // 00:00
    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(text = String.format("%02d:%02d", viewModel.minute, viewModel.second),
            fontFamily = dalmoori,
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    // 0 걸음
    Box(

        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)



    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(start=25.dp)
        ){
            Text(text = String.format("%d", viewModel.count),
                fontFamily = dalmoori,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Text(text =" 걸음",
                fontFamily = dalmoori,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Bottom)
            )}}


    if (viewModel.isWalkingEnd) {

        Row(
            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier.width(100.dp).height(85.dp).padding(top=25.dp)


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
            modifier = Modifier.fillMaxWidth().padding(top=5.dp)
        ) {
            val viewModel : MainInfoViewModel = viewModel()
            var findCode = viewModel.characterCode
            var chCode = CharacterCode.valueOf(findCode)
            val chA = painterResource(chCode.code)
            Image(painter = chA, contentDescription = null, modifier = Modifier.width(80.dp).height(80.dp))

        }
    }

    if (viewModel.isWalkingEnd) {

        Box() {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().padding(start=40.dp)
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
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .clickable {
                                viewModel.screenClick() {
                                    navController.navigate(WatchNavItem.Activity.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            }
                    )



                    Text(
                        text = "YES",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
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
                modifier = Modifier.fillMaxWidth().padding(end=40.dp)
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
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .clickable {
                                viewModel.screenClick() {
                                    navController.navigate(WatchNavItem.Activity.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            }
                    )



                    Text(
                        text = "NO",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
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
    }else {

        Box(modifier = Modifier
            .width(60.dp)
            .height(20.dp)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
        )
        {


            Image(
                painter = painterResource(id = R.drawable.blue_bnt),
                contentDescription = "blue_bnt",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

                    .clickable {
                        viewModel.screenClick() {

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
                fontSize = 10.sp,
                color = Color(0xFF0C4DA2)
            )


        }

    } }}


@Composable
fun BigWatch (  navController: NavHostController,
                  viewModel: WalkingViewModel)
{ Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxHeight()

) {

    Box(
        modifier = Modifier.height(10.dp) )
    // 00:00
    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(text = String.format("%02d:%02d", viewModel.minute, viewModel.second),
            fontFamily = dalmoori,
            fontSize = 20.sp
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    // 0 걸음
    Box(

        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)



    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(start=25.dp)
        ){
            Text(text = String.format("%d", viewModel.count),
                fontFamily = dalmoori,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Text(text =" 걸음",
                fontFamily = dalmoori,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Bottom)
            )}}


    if (viewModel.isWalkingEnd) {

        Row(
            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier.width(100.dp).height(100.dp).padding(top=30.dp)


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
            modifier = Modifier.fillMaxWidth().padding(top=5.dp)
        ) {
            val viewModel : MainInfoViewModel = viewModel()
            var findCode = viewModel.characterCode
            var chCode = CharacterCode.valueOf(findCode)
            val chA = painterResource(chCode.code)
            Image(painter = chA, contentDescription = null, modifier = Modifier.width(100.dp).height(100.dp))

        }
    }

    if (viewModel.isWalkingEnd) {

        Box(modifier = Modifier.padding(top=5.dp)) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth().padding(start=60.dp)
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
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .clickable {
                                viewModel.screenClick() {
                                    navController.navigate(WatchNavItem.Activity.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            }
                    )



                    Text(
                        text = "YES",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
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
                modifier = Modifier.fillMaxWidth().padding(end=60.dp)
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
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .clickable {
                                viewModel.screenClick() {
                                    navController.navigate(WatchNavItem.Activity.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            }
                    )



                    Text(
                        text = "NO",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
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
    }else {

        Box(modifier = Modifier
            .width(60.dp)
            .height(30.dp)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
        )
        {


            Image(
                painter = painterResource(id = R.drawable.blue_bnt),
                contentDescription = "blue_bnt",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

                    .clickable {
                        viewModel.screenClick() {

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
                fontSize = 14.sp,
                color = Color(0xFF0C4DA2)
            )


        }

    } }}
