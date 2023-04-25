package com.paymong.ui.watch.main


import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.paymong.domain.watch.main.MainConditionViewModel
import com.paymong.ui.theme.PaymongTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import com.paymong.common.R

@Composable
fun MainCondition() {
    val viewModel: MainConditionViewModel = viewModel()
    MainConditionUI(viewModel)
}

@Composable
fun MainConditionUI(
    viewModel: MainConditionViewModel
) {
    val contentAlignment = Alignment.Center

    Column(
            verticalArrangement = Arrangement.SpaceAround,
    modifier = Modifier.fillMaxHeight()
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            // * Health *
            Box(contentAlignment = contentAlignment, modifier =  Modifier.padding(top = 30.dp, end = 5.dp)) {
//            Text(text = String.format("%f", viewModel.health), textAlign = TextAlign.Center)
            Image(
                painter = painterResource(id = R.drawable.health ),
                contentDescription = "health",
                modifier = Modifier.size(30.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(70.dp),
                startAngle = 271f,
                endAngle = 270f,
                progress = viewModel.health,
                strokeWidth = 5.dp,
                indicatorColor = Color(android.graphics.Color.parseColor("#FFA3B1")),
            )
            }

            // * satiety *
            Box(contentAlignment = contentAlignment, modifier =  Modifier.padding(top = 30.dp, start = 5.dp)) {
//             Text(text = String.format("%f", viewModel.satiety), textAlign = TextAlign.Center)
                Image(
                    painter = painterResource(id = R.drawable.satiety ),
                    contentDescription = "health",
                    modifier = Modifier.size(30.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(70.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = viewModel.satiety,
                    strokeWidth = 5.dp,
                    indicatorColor = Color(android.graphics.Color.parseColor("#FCBF19")),
                )
            }

        }


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            // * Health *
            Box(contentAlignment = contentAlignment, modifier = Modifier.padding(bottom = 20.dp, end = 5.dp) ) {
//            Text(text = String.format("%f", viewModel.strength), textAlign = TextAlign.Center)
                Image(
                    painter = painterResource(id = R.drawable.strength ),
                    contentDescription = "strength",
                    modifier = Modifier.size(30.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(70.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = viewModel.strength,
                    strokeWidth = 5.dp,
                    indicatorColor = Color(android.graphics.Color.parseColor("#3BE368")),
                )
            }

            // * Sleep *
            Box(contentAlignment = contentAlignment,  modifier = Modifier.padding(bottom = 20.dp, start = 5.dp) ) {
//            Text(text = String.format("%f", viewModel.sleep), textAlign = TextAlign.Center)
                Image(
                    painter = painterResource(id = R.drawable.sleep ),
                    contentDescription = "strength",
                    modifier = Modifier.size(30.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(70.dp),
                    startAngle = 271f,
                    endAngle = 270f,
                    progress = viewModel.sleep,
                    strokeWidth = 5.dp,
                    indicatorColor = Color(android.graphics.Color.parseColor("#A1E9FF")),
                )
            }

        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainConditionPreview() {
    PaymongTheme {
        MainCondition()
    }
}