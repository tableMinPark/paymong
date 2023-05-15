package com.paymong.ui.watch.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.FeedViewModel
import com.paymong.common.code.SoundCode
import com.paymong.domain.watch.WatchViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background

@Composable
fun Feed(
    navController: NavHostController,
    soundViewModel: SoundViewModel,
    feedViewModel: FeedViewModel
) {
    LaunchedEffect(key1 = true) {
        feedViewModel.foodCategory = ""
        feedViewModel.currentFoodPosition = 0
    }
    Background(true)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val buttonHeight = if (screenWidthDp < 200) 95 else 100
    val buttonPadding = if (screenWidthDp < 200) 20 else 0
    val buttonFont = if (screenWidthDp < 200) 20 else 24

    // 선택시 이동
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
                    soundViewModel.soundPlay(SoundCode.FEED_BUTTON)
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
                    soundViewModel.soundPlay(SoundCode.FEED_BUTTON)
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