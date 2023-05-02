package com.paymong.ui.app.info_detail

import android.os.Build
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.paymong.common.code.MapCode
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.info_detail.InfoDetailViewModel
import com.paymong.domain.app.main.MainViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.app.component.BgGif

@Composable
fun InfoDetail(navController: NavController) {
    val viewModel: InfoDetailViewModel = viewModel()
    InfoDetailUI(navController, viewModel)
}

@Composable
fun Card(viewModel: InfoDetailViewModel){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val eggColors = listOf(PayMongGreen, PayMongGreen200, PayMongGreen)
        val firstColors = listOf(PayMongRed, PayMongRed200, PayMongRed)
        val secondColors = listOf(PayMongYellow, PayMongYellow200, PayMongYellow)
        val thirdColors = listOf(PayMongBlue, PayMongBlue200, PayMongBlue)
        val colors = when(viewModel.characterId.substring(2,3)){
            "0" -> eggColors
            "1" -> firstColors
            "2"-> secondColors
            else -> thirdColors
        }
        Box(
            modifier = Modifier
                .width(280.dp)
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors,
                        start = Offset.Zero, end = Offset.Infinite
                    )
                ),
        ) {}
        Box(
            modifier = Modifier
                .width(260.dp)
                .height(380.dp)
                .border(BorderStroke(3.dp, Color.White), shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
        }
        Info(viewModel.name, viewModel.characterId, viewModel.age, viewModel.weight)
    }
}

@Composable
fun Info(name: String, characterId: String, age: String, weight: Number){
    val chCode = CharacterCode.valueOf(characterId)
    val character = painterResource(chCode.code)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, fontFamily = dalmoori, fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 20.dp))
        Image(painter = character, contentDescription = null,
            modifier = Modifier.height(150.dp)
        )
        Text(text = age, fontFamily = dalmoori, fontSize = 20.sp, modifier = Modifier.padding(vertical = 20.dp))
        Text(text = String.format("%dkg",weight), fontFamily = dalmoori, fontSize = 20.sp)
    }
}

@Composable
fun InfoDetailUI(
    navController: NavController,
    viewModel: InfoDetailViewModel
    ) {
    val mainViewModel : MainViewModel = viewModel()
    val findBgCode = mainViewModel.background
    val bgCode = MapCode.valueOf(findBgCode)
    val bg = painterResource(bgCode.code)
    if(findBgCode == "MP000"){
        BgGif()
    } else {
        Image(
            painter = bg, contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
    Box(
        modifier = Modifier.fillMaxSize().clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { navController.navigate(AppNavItem.Main.route){
                popUpTo("main"){
                    inclusive = true
                }
            } }
        ),
        contentAlignment = Alignment.Center
    ){
        Card(viewModel)
    }

}

@Preview(showBackground = true)
@Composable
fun InfoDetailPreview() {
    val navController = rememberNavController()
    val viewModel : InfoDetailViewModel = viewModel()
    PaymongTheme {
//        InfoDetail(navController)
        Info(viewModel.name, viewModel.characterId, viewModel.age, viewModel.weight)
    }
}