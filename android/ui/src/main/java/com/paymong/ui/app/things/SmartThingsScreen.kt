package com.paymong.ui.app.things

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.SmartThingsViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.common.R
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun SmartThings(
    navController: NavController,
    smartThingsViewModel: SmartThingsViewModel = viewModel()
) {
    Scaffold(
        topBar = { TopBar("스마트싱스", navController, AppNavItem.Main.route) },
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)) {
            Column(
                modifier = Modifier.padding(30.dp)
            ) {
                Desc()
                Spacer(modifier = Modifier.height(20.dp))
                AddThings(navController)
                ThingsList()
            }
        }
    }
}

@Composable
fun Desc(){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(R.drawable.things), contentDescription = null, modifier = Modifier.size(100.dp))
                Spacer(modifier = Modifier.width(50.dp))
                Text(text = "스마트싱스로\n페이몽 관리", fontFamily = dalmoori, fontSize = 20.sp, color = Color.White, lineHeight = 40.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("청소기 등록,\n청소 하기", textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(painterResource(R.drawable.vacuum), contentDescription = null, modifier = Modifier.size(80.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Image(painterResource(R.drawable.rightbnt) , contentDescription = null, modifier = Modifier.size(30.dp))
                Spacer(modifier = Modifier.width(20.dp))
                Column() {
                    Text("페이몽도\n자동 청소", textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(painterResource(R.drawable.ch100), contentDescription = null, modifier = Modifier.size(80.dp))
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text("실생활과 연동된 페이몽을 즐겨보세요", fontFamily = dalmoori, fontSize = 15.sp, color = Color.White)
        }
    }
}

@Composable
fun AddThings(navController:NavController){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("나만의 스마트 싱스", fontFamily = dalmoori, fontSize = 15.sp, color = Color.White)
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Image(painterResource(R.drawable.btn_2_bg), contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { navController.navigate(AppNavItem.AddSmartThings.route) }
                        )
                        .width(110.dp)
                        .height(50.dp)
                )
                Text(
                    text = "추가하기", textAlign = TextAlign.Center,
                    fontFamily = dalmoori, fontSize = 15.sp, color = Color.White
                )
            }
        }
    }
}

@Composable
fun ThingsList(){

}

@Preview(showBackground = false)
@Composable
fun ThingsPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        AddThings(navController)
    }
}