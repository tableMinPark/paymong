package com.paymong.ui.watch.activity

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.ActivityViewModel
import com.paymong.domain.watch.feed.FeedBuyListViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.watch.feed.FeedBuyListUI
import kotlinx.coroutines.CoroutineScope

@Composable
fun Activity(navController: NavHostController) {
    val viewModel: ActivityViewModel = viewModel()
    ActivityUI(navController, viewModel)
}

@Composable
fun ActivityUI(
    navController: NavHostController,
    viewModel: ActivityViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.Training.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "훈련")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ){
            Button(
                onClick = { navController.navigate(WatchNavItem.Walking.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "산책")
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun ActivityPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        Activity(navController)
    }
}