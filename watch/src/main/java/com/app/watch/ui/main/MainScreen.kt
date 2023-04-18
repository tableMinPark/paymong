package com.app.watch.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.app.watch.theme.PaymongTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(
    animationState: MutableState<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
//            .padding(30.dp),
    ) {

        HorizontalPager(
            count = 4,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            page: Int ->
            when (page) {
                0 -> MainInteraction(animationState, pagerState, coroutineScope, navController)
                1 -> MainInfo(animationState)
                2 -> MainCondition()
                3 -> MainEnding(navController)
            }
        }
        HorizontalPagerIndicator(
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = MaterialTheme.colors.secondary,
            indicatorWidth = 6.dp,
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainPreview() {
    val animationState = remember { mutableStateOf("") }
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    PaymongTheme {
        Main(animationState = animationState, pagerState = pagerState, coroutineScope = coroutineScope, navController = navController)
    }
}