package com.paymong.ui.watch.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.TrainingViewModel
import com.paymong.ui.theme.PaymongTheme

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
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().clickable { viewModel.screenClick() {
                navController.navigate(WatchNavItem.Activity.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop =true
                }
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%02d:%02d", viewModel.second, viewModel.nanoSecond))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("%d", viewModel.count))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "대충 훈련하는 애니메이션")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (viewModel.isTrainingEnd) {
                Text(text = "훈련 종료")
            } else {
                Text(text = "연타")
            }
        }
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