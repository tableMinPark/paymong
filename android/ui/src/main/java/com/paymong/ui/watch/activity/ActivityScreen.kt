package com.paymong.ui.watch.activity

import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.paymong.common.R
import com.paymong.common.code.ToastMessage
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.refac.WatchViewModel
import com.paymong.domain.watch.refac.ActivityViewModel
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.landing.MainBackgroundGif

@Composable
fun Activity(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    activityViewModel: ActivityViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val context = LocalContext.current

    var buttonHeight = 100
    var buttonPadding = 0
    var buttonFont = 24

    var pointLogoSize = 15
    var fontSize = 15

    if (screenWidthDp < 200) {
        buttonHeight = 95
        buttonPadding = 20
        buttonFont = 20
        pointLogoSize = 10
        fontSize = 10
    }

    val img = painterResource(R.drawable.main_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()


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
                    SoundPlay(trainingViewModel, "Bnt");

                    if (watchViewModel.point < 50) {
                        Toast.makeText(context, ToastMessage.TRAINING_NOT_POINT.message, Toast.LENGTH_LONG).show()
                    }
                    else {
                    navController.navigate(WatchNavItem.Training.route)
                    } },
                modifier = Modifier
                    .size(width = 200.dp, height = buttonHeight.dp)
                    .weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(
                    text = "훈련",
                    fontFamily = dalmoori,
                    fontSize = buttonFont.sp,
                    modifier = Modifier.padding(top=buttonPadding.dp)
                )
                Row(modifier = Modifier.padding(top = 60.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.pointlogo),
                        contentDescription = "pointlogo",
                        modifier = Modifier
                            .size(pointLogoSize.dp)
                            .padding(bottom = 2.dp)
                    )
                    Text(
                        text = "-50",
                        textAlign = TextAlign.Center,
                        fontFamily = dalmoori,
                        fontSize = fontSize.sp,
                        color= Color.White,
                        modifier = Modifier.padding(start = 4.dp),
                    )}

            }
        }
        // * Line *
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(5.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Button(
                onClick = {  SoundPlay(trainingViewModel, "Bnt"); navController.navigate(WatchNavItem.Walking.route) },
                modifier = Modifier
                    .size(width = 200.dp, height = 100.dp)
                    .weight(1f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(
                    text = "산책",
                    fontFamily = dalmoori,
                    fontSize = buttonFont.sp,
                    modifier = Modifier.padding(bottom=buttonPadding.dp)
                )
            }
        }
    }
}