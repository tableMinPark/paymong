package com.paymong.ui.watch.feed

import android.media.SoundPool
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.paymong.domain.watch.feed.FeedViewModel
import com.paymong.common.R
import com.paymong.domain.watch.activity.WalkingViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.landing.MainBackgroundGif

@Composable
fun Feed(
    navController: NavHostController,
    feedViewModel: FeedViewModel
) {
    FeedUI(navController, feedViewModel)
}

@Composable
fun FeedUI(
    navController: NavHostController,
    feedViewModel: FeedViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    var buttonHeight = 100
    var buttonPadding = 0
    var buttonFont = 24
    if (screenWidthDp < 200) {
        buttonHeight = 95
        buttonPadding = 20
        buttonFont = 20
    }

    val img = painterResource(R.drawable.main_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()


    feedViewModel.currentFoodPosition = 0
    if(feedViewModel.foodCategory != ""){
        navController.navigate(WatchNavItem.FeedBuyList.route)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    ButtonSoundPlay(feedViewModel)
                    feedViewModel.foodCategory = "FD"
                },
                modifier = Modifier
                    .size(width = 200.dp, height = buttonHeight.dp)
                    .weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "밥",
                    fontFamily = dalmoori,
                    fontSize = buttonFont.sp,
                modifier = Modifier.padding(top=buttonPadding.dp))
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
                onClick = {
                    ButtonSoundPlay(feedViewModel)
                    feedViewModel.foodCategory = "SN"
                },
                modifier = Modifier
                    .size(width = 200.dp, height = buttonHeight.dp)
                    .weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "간식",
                    fontFamily = dalmoori,
                    fontSize = buttonFont.sp,
                    modifier = Modifier.padding(bottom=buttonPadding.dp))
            }
        }
    }
}


fun ButtonSoundPlay ( feedViewModel: FeedViewModel) {
    feedViewModel.soundPool.play(feedViewModel.buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
}



@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun FeedPreview() {
    val navController = rememberSwipeDismissableNavController()
    val feedViewModel: FeedViewModel = viewModel()
    PaymongTheme {
        Feed(navController, feedViewModel)
    }
}