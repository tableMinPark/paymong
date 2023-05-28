package com.paymong.ui.app.condition

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.ConditionViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.app.common.BgGif
import com.paymong.ui.app.common.TopBar

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Condition(
    navController: NavController,
    soundViewModel:SoundViewModel,
    conditionViewModel : ConditionViewModel = viewModel(),
) {
    Scaffold(
        topBar = { TopBar("${conditionViewModel.mongStats.name}는(은)?", navController, AppNavItem.Main.route, soundViewModel) },
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)){
            BgGif()
            Column(
                modifier = Modifier.padding(30.dp)
            ) {
                val healthImg = painterResource(R.drawable.health)
                val satietyImg = painterResource(R.drawable.satiety)
                val strengthImg = painterResource(R.drawable.strength)
                val sleepImg = painterResource(R.drawable.sleep)
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Component(healthImg, conditionViewModel.mongStats.health, PayMongRed.copy(alpha = 0.6f))
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Component(satietyImg, conditionViewModel.mongStats.satiety, PayMongYellow.copy(alpha = 0.6f))
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Component(strengthImg, conditionViewModel.mongStats.strength, PayMongGreen.copy(alpha = 0.6f))
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Component(sleepImg, conditionViewModel.mongStats.sleep, PayMongBlue200)
                }
            }
        }
    }
}

@Composable
fun Component(img: Painter, condition: Float, color: Color) { //이미지, 지수, 색
    Column(modifier = Modifier
        .padding(bottom = 10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Image(painter = img, contentDescription = null, modifier = Modifier.size(50.dp))
            Text("${(condition*100).toInt()} %", fontFamily = dalmoori, fontSize = 22.sp, color = Color.White)
        }
        Box {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .clip(CutCornerShape(10.dp))
                .background(Color.White)){
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = condition)
                    .clip(CutCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    .background(color = color)
                    .padding(8.dp)) {}
            }
        }
    }
}