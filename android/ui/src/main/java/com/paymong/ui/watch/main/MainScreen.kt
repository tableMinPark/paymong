package com.paymong.ui.watch.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.*
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.MapCode
import com.paymong.domain.watch.main.MainViewModel
import com.paymong.ui.theme.PayMongBlue
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.watch.landing.MainBackgroundGif
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    mainviewModel : MainViewModel
) {
    val bgCode = mainviewModel.mapCode
    val bg = painterResource(bgCode.code)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    if(bgCode==MapCode.MP000){
        MainBackgroundGif()
    } else{
        if(pagerState.currentPage!=1) {
            Box(
                modifier = Modifier.fillMaxSize().background(color = Color.Black.copy(alpha = 0.4f))
            )
        }
    }

    Column {
        HorizontalPager(
            count = 4,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
                page: Int ->
            when (page) {
                0 -> MainCondition(mainviewModel)
                1 -> MainInfo(animationState, mainviewModel)
                2 -> MainInteraction(animationState, pagerState, coroutineScope, navController, mainviewModel)
                3 -> MainInfoDetail(mainviewModel)
            }
        }
        HorizontalPagerIndicator(
            activeColor = PayMongNavy,
            inactiveColor = Color.White,
            indicatorWidth = 6.dp,
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 7.dp)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val mainviewModel: MainViewModel = viewModel()

    PaymongTheme {
        Main(animationState, pagerState, coroutineScope, navController, mainviewModel)
    }
}

