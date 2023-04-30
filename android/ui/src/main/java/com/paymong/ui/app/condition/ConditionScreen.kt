package com.paymong.ui.app.condition

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.R
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.condition.ConditionViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.app.component.TopBar

@Composable
fun Condition(navController: NavController) {
    val viewModel: ConditionViewModel = viewModel()
    ConditionUI(navController, viewModel)
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
        Box() {
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

@Composable
fun ConditionUI(
    navController: NavController,
    viewModel: ConditionViewModel
) {
    Scaffold(
        topBar = { TopBar("${viewModel.name}는(은)?", navController) },
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)){
//            val bg = painterResource(R.drawable.main_bg)
//            Image(bg, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
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
                    Component(healthImg, viewModel.health, PayMongRed.copy(alpha = 0.6f))
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Component(satietyImg, viewModel.satiety, PayMongYellow.copy(alpha = 0.6f))
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Component(strengthImg, viewModel.strength, PayMongGreen.copy(alpha = 0.6f))
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Component(sleepImg, viewModel.sleep, PayMongBlue200)
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ConditionPreview() {
    val navController = rememberNavController()
    PaymongTheme {
//        Condition(navController)
        val healthImg = painterResource(R.drawable.health)
        Component(healthImg, 0.35f, PayMongRed200)
    }
}