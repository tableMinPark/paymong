package com.paymong.ui.watch.feed

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.FeedViewModel
import com.paymong.common.R
import com.paymong.common.code.FoodCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.SoundCode
import com.paymong.domain.watch.WatchViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.LoadingGif
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun FeedBuyList(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    feedViewModel: FeedViewModel
) {
    LaunchedEffect(true) {
        feedViewModel.getFoodList(watchViewModel.point)
    }
    Background(MapCode.MP000)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val userPointBoxHeight = if (screenWidthDp < 200) 25 else 30
    val pointLogoSize = if (screenWidthDp < 200) 12 else 15
    val fontSize = if (screenWidthDp < 200) 12 else 15
    val bntImageSize = if (screenWidthDp < 200) 20 else 30
    val foodImageSize = if (screenWidthDp < 200) 75 else 85
    val purchasePointLogoSize = if (screenWidthDp < 200) 13 else 18
    val purchaseFontSize = if (screenWidthDp < 200) 12 else 15
    val purchaseBox = if (screenWidthDp < 200) 50 else 60
    val foodRowHeight = if (screenWidthDp < 200) 20 else 21
    val foodFontSize = if (screenWidthDp < 200) 16 else 21

    val payPointText = if (watchViewModel.point.toString().length > 5) {
        watchViewModel.point.toString().substring(0, 5) + "+"
    } else {
        watchViewModel.point.toString()
    }

    if(feedViewModel.isClick){
        feedViewModel.isClick = false
        Handler(Looper.getMainLooper()).postDelayed({
            watchViewModel.eating = false
        }, 3000)
        navController.navigate(WatchNavItem.Main.route) {
            coroutineScope.launch { pagerState.scrollToPage(1) }
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)

    ) {
        // * User Point *
        Box(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .height(userPointBoxHeight.dp)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
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
                    painter = painterResource(id = R.drawable.pointlogo),
                    contentDescription = "pointlogo",
                    modifier = Modifier
                        .size(pointLogoSize.dp)
                        .padding(bottom = 2.dp)
                )
                Text(
                    text = payPointText,
                    textAlign = TextAlign.Center,
                    fontFamily = dalmoori,
                    fontSize = fontSize.sp,
                    color = Color(0xFF0C4DA2),
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
        }

        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            // 왼쪽 버튼
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        soundViewModel.soundPlay(SoundCode.FEED_BUTTON)
                        feedViewModel.prevButtonClick(watchViewModel.point)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leftbnt),
                        contentDescription = "leftbnt",
                        modifier = Modifier.size(bntImageSize.dp)
                    )
                }
            }

            // 음식
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxHeight(1f)) {
                    Row(
                        modifier = Modifier
                            .height(foodRowHeight.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = feedViewModel.name,
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            fontSize = foodFontSize.sp
                        )
                    }

                    var foodImg = R.drawable.none
                    if (feedViewModel.foodCode != "") {
                        foodImg = FoodCode.valueOf(feedViewModel.foodCode).code
                    }

                    Row(
                        modifier = Modifier
                            .height(foodImageSize.dp)
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        if (feedViewModel.foodCode == "") {
                            Box(
                                modifier = Modifier
                                    .width(foodImageSize.dp)
                                    .height(foodImageSize.dp)
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            ) {
                                LoadingGif()
                            }
                        } else {
                            Image(
                                painter = painterResource(foodImg),
                                contentDescription = "foodImg",
                                modifier = Modifier.size(foodImageSize.dp)
                            )
                        }
                    }
                }
            }

            // 오른쪽 버튼
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        soundViewModel.soundPlay(SoundCode.FEED_BUTTON)
                        feedViewModel.nextButtonClick(watchViewModel.point)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier.fillMaxHeight(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rightbnt),
                        contentDescription = "rightbnt",
                        modifier = Modifier.size(bntImageSize.dp)
                    )
                }
            }
        }

        //  구매 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(purchaseBox.dp)
                .padding(top = 15.dp)
        ) {
            Button(
                onClick = {
                    if(feedViewModel.isCanBuy){
                        soundViewModel.soundPlay(SoundCode.COIN_SOUND)
                        feedViewModel.isClick = true
                        animationState.value = AnimationCode.Feed
                        feedViewModel.selectButtonClick(feedViewModel.currentCategory, watchViewModel)
                    } else {
                        soundViewModel.soundPlay(SoundCode.COIN_SOUND)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    if(!feedViewModel.isCanBuy){
                        Image(
                            painter = painterResource(id = R.drawable.gray_btn),
                            contentDescription = "gray_btn",
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.blue_bnt),
                            contentDescription = "blue_bnt",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {

                        if(!feedViewModel.isCanBuy){
                        Image(
                            painter = painterResource(id = R.drawable.point_logo_gray),
                            contentDescription = "point_logo_gray",
                            modifier = Modifier
                                .size(purchasePointLogoSize.dp)
                                .padding(bottom = 2.dp)
                        ) } else {
                            Image(
                                painter = painterResource(id = R.drawable.pointlogo),
                                contentDescription = "pointlogo",
                                modifier = Modifier
                                    .size(purchasePointLogoSize.dp)
                                    .padding(bottom = 2.dp)
                            )

                        }
                        if(!feedViewModel.isCanBuy){
                        Text(
                            text = String.format(" %d", feedViewModel.price),
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            color = Color.DarkGray,
                            fontSize = purchaseFontSize.sp
                        ) } else {
                            Text(
                                text = String.format(" %d", feedViewModel.price),
                                textAlign = TextAlign.Center,
                                fontFamily = dalmoori,
                                color = Color(0xFF0C4DA2),
                                fontSize = purchaseFontSize.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
