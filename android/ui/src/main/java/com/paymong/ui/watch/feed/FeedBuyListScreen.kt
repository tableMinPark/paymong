package com.paymong.ui.watch.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.feed.FeedBuyListViewModel
import com.paymong.common.R
import com.paymong.ui.theme.PayMongBlue
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.Purple700
import com.paymong.ui.theme.dalmoori
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedBuyList(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    val viewModel: FeedBuyListViewModel = viewModel()
    FeedBuyListUI(animationState, pagerState, coroutineScope, navController, viewModel)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedBuyListUI(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    viewModel: FeedBuyListViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp


    val img = painterResource(R.drawable.main_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)

    if (screenWidthDp < 200) {
        SmallWatch(animationState, pagerState, coroutineScope, navController, viewModel)
    }
    else {
        BigWatch(animationState, pagerState, coroutineScope, navController, viewModel)
    }





}



@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun FeedBuyListPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    PaymongTheme {
        FeedBuyList(animationState, pagerState, coroutineScope, navController)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SmallWatch(  animationState: MutableState<AnimationCode>,
                 pagerState: PagerState,
                 coroutineScope: CoroutineScope,
                 navController: NavHostController,
                 viewModel: FeedBuyListViewModel
) {

    val payPointText = if (viewModel.payPoint.toString().length > 5) {
        viewModel.payPoint.toString().substring(0, 5) + "+"
    } else {
        viewModel.payPoint.toString()
    }

    Column(
//        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)

    ) {



        // * User Point *
        Box(modifier = Modifier
            .padding(bottom = 15.dp)
            .height(20.dp)

            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)


        )
        {

            Image(
                painter = painterResource(id = R.drawable.pointbackground),
                contentDescription = "pointbackground",
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()

            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pointlogo ),
                    contentDescription = "pointlogo",
                    modifier = Modifier
                        .size(10.dp)
                        .padding(bottom = 2.dp)
                )
                Text(
                    text = payPointText,
                    textAlign = TextAlign.Center,
                    fontFamily = dalmoori,
                    fontSize = 10.sp,
                    color = Color(0xFF0C4DA2),
                    modifier = Modifier.padding(start = 4.dp),


                    )

            }
        }

        // * name *


        // * foodCode *

//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = viewModel.foodCode, textAlign = TextAlign.Center)
//        }



        // * foodImg & Light button & Left button *



        Box(modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)


        )


        {

            Row(
                horizontalArrangement = Arrangement.Start,

                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { viewModel.prevButtonClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftbnt ),
                        contentDescription = "leftbnt",
                        modifier = Modifier.size(25.dp)
                    )
                }

            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val drawableId = when (viewModel.foodCode) {
                    "FD000" -> R.drawable.fd000 // 별사탕
                    "FD001" -> R.drawable.fd001 // 사과
                    "FD002" -> R.drawable.fd002 // 삼각김밥
                    "FD003" -> R.drawable.fd003 // 샌드위치
                    "FD004" -> R.drawable.fd004 // 피자
                    "FD005" -> R.drawable.fd005 // 닭다리
                    "FD006" -> R.drawable.fd006 // 스테이크
                    "FD007" -> R.drawable.fd007 // 우주음식

                    "SN000" -> R.drawable.sn000 // 초콜릿
                    "SN001" -> R.drawable.sn001 // 사탕
                    "SN002" -> R.drawable.sn002 // 음료수
                    "SN003" -> R.drawable.sn003 // 쿠키
                    "SN004" -> R.drawable.sn004 // 케이크
                    "SN005" -> R.drawable.sn005 // 감튀

                    else -> R.drawable.pointlogo
                }
                Column(modifier = Modifier.fillMaxHeight(1f)) {
                    Row( modifier = Modifier.height(20.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,) {
                        Text(text = viewModel.name, textAlign = TextAlign.Center, fontFamily = dalmoori,
                            fontSize = 16.sp)
                    }

                    Row(modifier = Modifier.height(80.dp).fillMaxWidth().padding(top=10.dp),
                        horizontalArrangement = Arrangement.Center,) {
                        Image(
                            painter = painterResource(id = drawableId),
                            contentDescription = "foodImg",
                            modifier = Modifier.size(80.dp)

                        )
                    }
                }




            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    onClick = { viewModel.nextButtonClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rightbnt ),
                        contentDescription = "rightbnt",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }


        // * button *
        Box(

            modifier = Modifier
                .fillMaxWidth(1f)
                .height(50.dp)
                .padding(top = 15.dp)
//                .wrapContentHeight(Alignment.CenterVertically)
//                .wrapContentWidth(Alignment.CenterHorizontally)



        ) {


            Button(
                onClick = {
                    viewModel.selectButtonClick()
                    animationState.value = AnimationCode.Feed
                    coroutineScope.launch { pagerState.scrollToPage(1) }
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

                ,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.blue_bnt ),
                        contentDescription = "blue_bnt",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {


                        Image(
                            painter = painterResource(id = R.drawable.pointlogo ),
                            contentDescription = "pointlogo",
                            modifier = Modifier
                                .size(13.dp)
                                .padding(bottom = 2.dp)
                        )
                        Text(
                            text = String.format(" %d", viewModel.price),
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            color = Color(0xFF0C4DA2) ,
                            fontSize = 12.sp
                        )

                    }

                }


            }


        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BigWatch(  animationState: MutableState<AnimationCode>,
                 pagerState: PagerState,
                 coroutineScope: CoroutineScope,
                 navController: NavHostController,
                 viewModel: FeedBuyListViewModel
) {

    val payPointText = if (viewModel.payPoint.toString().length > 5) {
        viewModel.payPoint.toString().substring(0, 5) + "+"
    } else {
        viewModel.payPoint.toString()
    }

    Column(
//        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)

    ) {



        // * User Point *
        Box(modifier = Modifier
            .padding(bottom = 15.dp)
            .height(30.dp)

            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)


        )
        {

            Image(
                painter = painterResource(id = R.drawable.pointbackground),
                contentDescription = "pointbackground",
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()

            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pointlogo ),
                    contentDescription = "pointlogo",
                    modifier = Modifier
                        .size(15.dp)
                        .padding(bottom = 2.dp)
                )
                Text(
                    text = payPointText,
                    textAlign = TextAlign.Center,
                    fontFamily = dalmoori,
                    fontSize = 15.sp,
                    color = Color(0xFF0C4DA2),
                    modifier = Modifier.padding(start = 4.dp),


                    )

            }
        }

        // * name *


        // * foodCode *

//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = viewModel.foodCode, textAlign = TextAlign.Center)
//        }



        // * foodImg & Light button & Left button *



        Box(modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)


        )


        {

            Row(
                horizontalArrangement = Arrangement.Start,

                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { viewModel.prevButtonClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftbnt ),
                        contentDescription = "leftbnt",
                        modifier = Modifier.size(30.dp)
                    )
                }

            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val drawableId = when (viewModel.foodCode) {
                    "FD000" -> R.drawable.fd000 // 별사탕
                    "FD001" -> R.drawable.fd001 // 사과
                    "FD002" -> R.drawable.fd002 // 삼각김밥
                    "FD003" -> R.drawable.fd003 // 샌드위치
                    "FD004" -> R.drawable.fd004 // 피자
                    "FD005" -> R.drawable.fd005 // 닭다리
                    "FD006" -> R.drawable.fd006 // 스테이크
                    "FD007" -> R.drawable.fd007 // 우주음식

                    "SN000" -> R.drawable.sn000 // 초콜릿
                    "SN001" -> R.drawable.sn001 // 사탕
                    "SN002" -> R.drawable.sn002 // 음료수
                    "SN003" -> R.drawable.sn003 // 쿠키
                    "SN004" -> R.drawable.sn004 // 케이크
                    "SN005" -> R.drawable.sn005 // 감튀

                    else -> R.drawable.pointlogo
                }
                Column(modifier = Modifier.fillMaxHeight(1f)) {
                    Row( modifier = Modifier.height(21.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,) {
                        Text(text = viewModel.name, textAlign = TextAlign.Center, fontFamily = dalmoori,
                            fontSize = 21.sp)
                    }

                    Row(modifier = Modifier.height(85.dp).fillMaxWidth().padding(top=10.dp),
                        horizontalArrangement = Arrangement.Center,) {
                        Image(
                            painter = painterResource(id = drawableId),
                            contentDescription = "foodImg",
                            modifier = Modifier.size(85.dp)

                        )
                    }
                }




            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    onClick = { viewModel.nextButtonClick() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rightbnt ),
                        contentDescription = "rightbnt",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }


        // * button *
        Box(

            modifier = Modifier
                .fillMaxWidth(1f)
                .height(60.dp)
                .padding(top = 15.dp)
//                .wrapContentHeight(Alignment.CenterVertically)
//                .wrapContentWidth(Alignment.CenterHorizontally)



        ) {


            Button(
                onClick = {
                    viewModel.selectButtonClick()
                    animationState.value = AnimationCode.Feed
                    coroutineScope.launch { pagerState.scrollToPage(1) }
                    navController.navigate(WatchNavItem.Main.route){
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop =true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()

                ,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.blue_bnt ),
                        contentDescription = "blue_bnt",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {


                        Image(
                            painter = painterResource(id = R.drawable.pointlogo ),
                            contentDescription = "pointlogo",
                            modifier = Modifier
                                .size(18.dp)
                                .padding(bottom = 2.dp)
                        )
                        Text(
                            text = String.format(" %d", viewModel.price),
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            color = Color(0xFF0C4DA2) ,
                            fontSize = 18.sp
                        )

                    }

                }


            }


        }
    }
}