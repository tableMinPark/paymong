package com.paymong.ui.watch.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.feed.FeedBuyListViewModel
import com.paymong.domain.watch.feed.FeedViewModel
import com.paymong.common.R
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.activity.BigWatch
import com.paymong.ui.watch.activity.SmallWatch
import com.paymong.ui.watch.landing.MainBackgroundGif

@Composable
fun Feed(navController: NavHostController) {
    val viewModel: FeedViewModel = viewModel()
    FeedUI(navController, viewModel)
}

@Composable
fun FeedUI(
    navController: NavHostController,
    viewModel: FeedViewModel
) {


    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    val img = painterResource(R.drawable.main_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()

    if (screenWidthDp < 200) {
        SmallWatch( navController, viewModel)
    }
    else {
        BigWatch( navController, viewModel)
    }


}



@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun FeedPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        Feed(navController)
    }
}

@Composable
fun SmallWatch(    navController: NavHostController,
                   viewModel: FeedViewModel) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.FeedBuyList.route + "/RICE") },
                modifier = Modifier.size(width = 200.dp, height = 95.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "밥",
                    fontFamily = dalmoori,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top= 20.dp))
            }
        }
        // * Line *
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(5.dp))
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier

                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.FeedBuyList.route + "/SNACK") },
                modifier = Modifier.size(width = 200.dp, height = 95.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "간식",
                    fontFamily = dalmoori,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom= 20.dp))

            }
        }
    }
}


@Composable
fun BigWatch(    navController: NavHostController,
                   viewModel: FeedViewModel) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.FeedBuyList.route + "/RICE") },
                modifier = Modifier.size(width = 200.dp, height = 100.dp).weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "밥",
                    fontFamily = dalmoori,
                    fontSize = 24.sp)
            }
        }
        // * Line *
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(5.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier

                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Button(
                onClick = { navController.navigate(WatchNavItem.FeedBuyList.route + "/SNACK") },
                modifier = Modifier.size(width = 200.dp, height = 100.dp).weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "간식",
                    fontFamily = dalmoori,
                    fontSize = 24.sp)

            }
        }
    }
}