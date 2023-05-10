package com.paymong.ui.watch.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.common.R
import com.paymong.domain.watch.refac.WatchViewModel
import com.paymong.ui.theme.*

@Composable
fun MainCondition(
    watchViewModel: WatchViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    val imageSize = if(screenWidthDp < 200) 25 else 35
    val circularSize = if(screenWidthDp < 200) 60 else 70
    val strokeWidth = if(screenWidthDp < 200) 4 else 5

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
                    painter = painterResource(id = R.drawable.health),
                    contentDescription = "health",
                    modifier = Modifier.size(imageSize.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(circularSize.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = watchViewModel.mongStats.health,
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
                    progress = watchViewModel.mongStats.satiety,
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
                    progress = watchViewModel.mongStats.strength,
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
                    progress = watchViewModel.mongStats.sleep,
                    strokeWidth = strokeWidth.dp,
                    indicatorColor = PayMongBlue,
                )
            }

        }
    }
}