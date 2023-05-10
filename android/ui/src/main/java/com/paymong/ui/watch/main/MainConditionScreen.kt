package com.paymong.ui.watch.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.common.R
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.theme.*

@Composable
fun MainCondition(
    mainviewModel : WatchViewModel
) {
    MainConditionUI(mainviewModel)
}

@Composable
fun MainConditionUI(
    mainviewModel: WatchViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    var imageSize = 30
    var circularSize = 70
    var strokeWidth = 5

    if (screenWidthDp < 200) {
        imageSize = 25
        circularSize = 60
        strokeWidth= 4
    }



    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier =  Modifier.padding(top = 30.dp, end = 5.dp))
            {
                Image(
                    painter = painterResource(id = R.drawable.health ),
                    contentDescription = "health",
                    modifier = Modifier.size(imageSize.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(circularSize.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = mainviewModel.mongStats.health,
                    strokeWidth = strokeWidth.dp,
                    indicatorColor = PayMongRed,
                )
            }

            Box(contentAlignment = Alignment.Center, modifier =  Modifier.padding(top = 30.dp, start = 5.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.satiety ),
                    contentDescription = "satiety",
                    modifier = Modifier.size(imageSize.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(circularSize.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = mainviewModel.mongStats.satiety,
                    strokeWidth = strokeWidth.dp,
                    indicatorColor = PayMongYellow,
                )
            }

        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(bottom = 20.dp, end = 5.dp) ) {
                Image(
                    painter = painterResource(id = R.drawable.strength ),
                    contentDescription = "strength",
                    modifier = Modifier.size(imageSize.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(circularSize.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = mainviewModel.mongStats.strength,
                    strokeWidth = strokeWidth.dp,
                    indicatorColor = PayMongGreen,
                )
            }

            Box(contentAlignment = Alignment.Center,  modifier = Modifier.padding(bottom = 20.dp, start = 5.dp) ) {
                Image(
                    painter = painterResource(id = R.drawable.sleep ),
                    contentDescription = "sleep",
                    modifier = Modifier.size(imageSize.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(circularSize.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = mainviewModel.mongStats.sleep,
                    strokeWidth = strokeWidth.dp,
                    indicatorColor = PayMongBlue,
                )
            }

        }
    }
}








@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainConditionPreview() {
    val mainviewModel : WatchViewModel = viewModel()
    PaymongTheme {
        MainCondition(mainviewModel)
    }
}