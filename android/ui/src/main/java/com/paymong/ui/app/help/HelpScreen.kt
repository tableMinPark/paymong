package com.paymong.ui.app.help

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.R
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.*

@Composable
fun Help(
    navController: NavController,
    soundViewModel: SoundViewModel
) {
    Scaffold(
        topBar = { TopBar("도움말", navController, AppNavItem.Main.route, soundViewModel) },
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)){
            BgGif()

            Desc()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Desc() {
    HorizontalPager(
        count = 5,
        state = rememberPagerState(),
    ) { page ->

        when(page){
            0-> Page1()
            1-> Page2()
            2-> Page3()
            3-> Page4()
            4-> Page5()
        }
    }
}

@Composable
fun Page1(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("1. 페이몽 탄생", fontFamily = dalmoori, color = Color.White, fontSize = 35.sp, modifier = Modifier
            .background(color = Color.Black.copy(alpha = 0.4f))
            .padding(10.dp), letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(50.dp))
        Text("스마트폰에서 페이몽 탄생", fontFamily = dalmoori, color = PayMongGreen, fontSize = 20.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Text("알은 10분 후 부화 합니다.", fontFamily = dalmoori, color = PayMongRed, fontSize = 18.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(50.dp))
        Image(painterResource(R.drawable.ch004), contentDescription = null, modifier = Modifier.size(150.dp))
    }
}

@Composable
fun Page2(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("2. 페이몽 돌보기", fontFamily = dalmoori, color = Color.White, fontSize = 35.sp, modifier = Modifier
            .background(color = Color.Black.copy(alpha = 0.4f))
            .padding(10.dp), letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text("페이몽의 상태를 확인하기!", fontFamily = dalmoori, color = PayMongRed, fontSize = 18.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Image(painterResource(R.drawable.help_status), contentDescription = null, modifier = Modifier.width(300.dp))
        Spacer(modifier = Modifier.height(60.dp))
        Text("워치에서 페이몽을 돌보기", fontFamily = dalmoori, color = PayMongGreen, fontSize = 18.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Image(painterResource(R.drawable.help_interaction), contentDescription = null, modifier = Modifier.size(250.dp))
    }
}

@Composable
fun Page3(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("3. 페이몽 성장", fontFamily = dalmoori, color = Color.White, fontSize = 35.sp, modifier = Modifier
            .background(color = Color.Black.copy(alpha = 0.4f))
            .padding(10.dp), letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Image(painterResource(R.drawable.help_growth), contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp))
        Spacer(modifier = Modifier.height(40.dp))
        Text("페이몽을 돌보고 성장시키세요!", fontFamily = dalmoori, color = PayMongRed, fontSize = 18.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("다양한 페이몽을 모아보세요!", fontFamily = dalmoori, color = PayMongRed, fontSize = 20.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(60.dp))
        Image(painterResource(R.drawable.help_training), contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp))
        Spacer(modifier = Modifier.height(40.dp))
        Text("훈련과 산책으로 페이몽 성장!!", fontFamily = dalmoori, color = PayMongGreen, fontSize = 18.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("페이몽의 능력치를 올려보세요!", fontFamily = dalmoori, color = PayMongGreen, fontSize = 20.sp, letterSpacing = 2.sp)
    }
}

@Composable
fun Page4(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("4. 배틀", fontFamily = dalmoori, color = Color.White, fontSize = 35.sp, modifier = Modifier
            .background(color = Color.Black.copy(alpha = 0.4f))
            .padding(10.dp), letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Image(painterResource(R.drawable.help_battle), contentDescription = null, modifier = Modifier.width(250.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text("근처에 있는 상대방과 배틀!", fontFamily = dalmoori, color = Color.White, fontSize = 18.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text("총 10턴 동안, 배틀을 진행", fontFamily = dalmoori, color = Color.White, fontSize = 18.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Image(painterResource(R.drawable.help_attack), contentDescription = null, modifier = Modifier.width(180.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text("공격할 방향을 선택", fontFamily = dalmoori, color = PayMongRed, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Text("상대방과 다른 방향을 선택 > 공격 성공!", fontFamily = dalmoori, color = PayMongRed, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Image(painterResource(R.drawable.help_defence), contentDescription = null, modifier = Modifier.width(160.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text("수비할 방향을 선택", fontFamily = dalmoori, color = PayMongGreen, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Text("상대방과 같은 방향을 선택 > 수비 성공!", fontFamily = dalmoori, color = PayMongGreen, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text("배틀에서 승리하여 보상을 획득하세요!", fontFamily = dalmoori, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Page5(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("5. 포인트 (성星)", fontFamily = dalmoori, color = Color.White, fontSize = 35.sp, modifier = Modifier
            .background(color = Color.Black.copy(alpha = 0.4f))
            .padding(10.dp), letterSpacing = 3.sp)
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painterResource(R.drawable.help_pay), contentDescription = null, modifier = Modifier)
            Spacer(modifier = Modifier.width(10.dp))
            Text("삼성페이로 결제", fontFamily = dalmoori, color = PayMongBlue, fontSize = 18.sp, letterSpacing = 2.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Image(painterResource(R.drawable.help_next), contentDescription = null, modifier = Modifier)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("포인트 (성星) 획득", fontFamily = dalmoori, color = PayMongBlue, fontSize = 18.sp, letterSpacing = 2.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Image(painterResource(R.drawable.help_point), contentDescription = null, modifier = Modifier)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text("포인트를 모아 몽을 키우세요!", fontFamily = dalmoori, color = PayMongBlue, fontSize = 20.sp, letterSpacing = 2.sp)
        Spacer(modifier = Modifier.height(60.dp))
        Image(painterResource(R.drawable.help_map), contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp))
        Spacer(modifier = Modifier.height(40.dp))
        Text("결제한 장소의 맵을 모아보세요!", fontFamily = dalmoori, color = PayMongGreen, fontSize = 20.sp, letterSpacing = 2.sp)
    }
}

@Preview(showBackground = false)
@Composable
fun HelpPreview() {
    PaymongTheme {
        Page5()
    }
}