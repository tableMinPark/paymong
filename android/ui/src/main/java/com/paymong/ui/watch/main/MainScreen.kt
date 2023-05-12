package com.paymong.ui.watch.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.domain.watch.WatchViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.MainBackgroundGif
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    watchViewModel : WatchViewModel,
    soundViewModel: SoundViewModel
) {
    // 배경
    Background(false)

    if(watchViewModel.mapCode == MapCode.MP000){
        MainBackgroundGif()
    } else if (pagerState.currentPage != 1){
        Box( modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.4f)) )
    }

    Column {
        HorizontalPager(
            count = 4,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
                page: Int ->
            when (page) {
                0 -> MainCondition(watchViewModel)
                1 -> MainInfo(animationState, watchViewModel)
                2 -> MainInteraction(animationState, pagerState, coroutineScope, navController, watchViewModel, soundViewModel)
                3 -> MainInfoDetail(watchViewModel)
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
    Box() {
        if (watchViewModel.stateCode == MongStateCode.CD002) {
            Row(modifier = Modifier.fillMaxSize().background(color = Color.Black.copy(0.4f))) {
            }
        }
    }
}

