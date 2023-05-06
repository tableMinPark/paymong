package com.paymong.ui.app.info_detail

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.code.MapCode
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.AppViewModel
import com.paymong.domain.app.InfoDetailViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.app.component.BgGif

@Composable
fun InfoDetail(
    navController: NavController,
    appViewModel : AppViewModel
) {
    val findBgCode = appViewModel.mapCode
    val bg = painterResource(findBgCode.code)

    if(findBgCode == MapCode.MP000){
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
        Card(appViewModel)
    }

}

@Composable
fun Card(appViewModel : AppViewModel){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val eggColors = listOf(PayMongGreen, PayMongGreen200, PayMongGreen)
        val firstColors = listOf(PayMongRed, PayMongRed200, PayMongRed)
        val secondColors = listOf(PayMongYellow, PayMongYellow200, PayMongYellow)
        val thirdColors = listOf(PayMongBlue, PayMongBlue200, PayMongBlue)
        val colors = when(appViewModel.mong.mongCode.code.substring(2,3)){            // 내부에서 계산해서 view 로 넘겨야함
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
        Info(appViewModel)
        // appViewModel.mongInfo.name, appViewModel.mongInfo.mongCode, appViewModel.getAge(), appViewModel.mongInfo.weight
        // name: String, mongCode: CharacterCode, age: String, weight: Number
    }
}

@Composable
fun Info(
    appViewModel: AppViewModel,
    infoDetailViewModel: InfoDetailViewModel = viewModel()
){
    val character = painterResource(appViewModel.mong.mongCode.resourceCode)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = appViewModel.mong.name, fontFamily = dalmoori, fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 20.dp))
        Image(painter = character, contentDescription = null,
            modifier = Modifier.height(150.dp)
        )
        Text(text = infoDetailViewModel.age, fontFamily = dalmoori, fontSize = 20.sp, modifier = Modifier.padding(vertical = 20.dp))
        Text(text = String.format("%dg", infoDetailViewModel.mongInfo.weight), fontFamily = dalmoori, fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun InfoDetailPreview() {
    val navController = rememberNavController()
    val viewModel : AppViewModel = viewModel()
    PaymongTheme {
//        InfoDetail(navController)
//        Info(viewModel.name, viewModel.characterId, viewModel.age, viewModel.weight)
    }
}