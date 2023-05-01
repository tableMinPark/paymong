package com.paymong.ui.watch.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.CircularProgressIndicator
import com.paymong.domain.watch.main.MainConditionViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.common.R
import com.paymong.ui.theme.*

@Composable
fun MainCondition() {
    val viewModel: MainConditionViewModel = viewModel()
    MainConditionUI(viewModel)
}

@Composable
fun MainConditionUI(
    viewModel: MainConditionViewModel
) {


    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp


    Column(
            verticalArrangement = Arrangement.SpaceAround,
    modifier = Modifier.fillMaxHeight()
    ) {


        if (screenWidthDp < 200) {
            SmallWatch(viewModel)
        }
        else {
            BigWatch( viewModel)
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


@Composable
fun SmallWatch( viewModel: MainConditionViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().height(90.dp)
    ) {



        // * Health *
        Box(contentAlignment = Alignment.Center, modifier =  Modifier.padding(top = 30.dp, end = 5.dp))
        {
//            Text(text = String.format("%f", viewModel.health), textAlign = TextAlign.Center)
            Image(
                painter = painterResource(id = R.drawable.health ),
                contentDescription = "health",
                modifier = Modifier.size(25.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                startAngle = 271f,
                endAngle = 270f,
                progress = viewModel.health,
                strokeWidth = 4.dp,
                indicatorColor = PayMongRed,
            )
        }

        // * satiety *
        Box(contentAlignment = Alignment.Center, modifier =  Modifier.padding(top = 30.dp, start = 5.dp)) {
//             Text(text = String.format("%f", viewModel.satiety), textAlign = TextAlign.Center)
            Image(
                painter = painterResource(id = R.drawable.satiety ),
                contentDescription = "satiety",
                modifier = Modifier.size(25.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                startAngle = 271f,
                endAngle = 270f,
                progress = viewModel.satiety,
                strokeWidth = 4.dp,
                indicatorColor = PayMongYellow,
            )
        }

    }


    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(top=10.dp)
    ) {

        // * Health *
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(bottom = 20.dp, end = 5.dp) ) {
//            Text(text = String.format("%f", viewModel.strength), textAlign = TextAlign.Center)
            Image(
                painter = painterResource(id = R.drawable.strength ),
                contentDescription = "strength",
                modifier = Modifier.size(25.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                startAngle = 271f,
                endAngle = 270f,
                progress = viewModel.strength,
                strokeWidth = 4.dp,
                indicatorColor = PayMongGreen,
            )
        }


        // * Sleep *
        Box(contentAlignment = Alignment.Center,  modifier = Modifier.padding(bottom = 20.dp, start = 5.dp) ) {
//            Text(text = String.format("%f", viewModel.sleep), textAlign = TextAlign.Center)
            Image(
                painter = painterResource(id = R.drawable.sleep ),
                contentDescription = "sleep",
                modifier = Modifier.size(25.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                startAngle = 271f,
                endAngle = 270f,
                progress = viewModel.sleep,
                strokeWidth = 4.dp,
                indicatorColor = PayMongBlue,
            )
        }

    }
}


@Composable
fun BigWatch( viewModel: MainConditionViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {



        // * Health *
        Box(contentAlignment = Alignment.Center, modifier =  Modifier.padding(top = 30.dp, end = 5.dp))
        {
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
                indicatorColor = PayMongRed,
            )
        }

        // * satiety *
        Box(contentAlignment = Alignment.Center, modifier =  Modifier.padding(top = 30.dp, start = 5.dp)) {
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
                indicatorColor = PayMongYellow,
            )
        }

    }


    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        // * Health *
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(bottom = 20.dp, end = 5.dp) ) {
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
                indicatorColor = PayMongGreen,
            )
        }


        // * Sleep *
        Box(contentAlignment = Alignment.Center,  modifier = Modifier.padding(bottom = 20.dp, start = 5.dp) ) {
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
                indicatorColor = PayMongBlue,
            )
        }

    }
}


