package com.app.watch.ui.interaction.feed

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.app.watch.navigation.NavItem
import com.app.watch.theme.PaymongTheme

@Composable
fun Feed( navController: NavHostController ) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                // nav -> FeedBuyListScreen("[음식구분코드]")
                onClick = { navController.navigate(NavItem.FeedBuyList.route + "/RICE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "밥")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Button(
                // nav -> FeedBuyListScreen("[음식구분코드]")
                onClick = { navController.navigate(NavItem.FeedBuyList.route + "/SNACK") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "간식")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Button(
                // nav -> FeedBuyListScreen("[음식구분코드]")
                onClick = { navController.navigate(NavItem.FeedBuyList.route + "/MEDICINE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "약")
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun FeedPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        Feed( navController)
    }
}