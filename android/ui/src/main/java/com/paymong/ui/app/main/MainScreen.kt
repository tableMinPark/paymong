package com.paymong.ui.app.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.main.MainViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.common.R
import com.paymong.common.code.BackgroundCode
import com.paymong.ui.theme.dalmoori
import java.text.NumberFormat
import java.util.*

@Composable
fun Main(navController: NavController) {
    val viewModel: MainViewModel = viewModel()
    MainUI(navController, viewModel)
}

@Composable
fun Help(navController: NavController){
    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate(AppNavItem.Help.route) }
                )
                .height(40.dp)
                .padding(horizontal = 20.dp)
        )
        Text(text = "?", textAlign = TextAlign.Center,
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Info(navController: NavController){
    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate(AppNavItem.InfoDetail.route + "/characterId") }
                )
                .width(40.dp)
        )
        Text(text = "i", textAlign = TextAlign.Center,
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Point(navController: NavController){
    val viewModel : MainViewModel = viewModel()
    Box(
        contentAlignment = Alignment.Center
    ){
        val pointBg = painterResource(R.drawable.pointbackground)
        val pointLogo = painterResource(R.drawable.pointlogo)
        Image(painter = pointBg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate(AppNavItem.PayPoint.route + "/memberId") }
                )
                .width(200.dp)
        )
        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = pointLogo, contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .padding(end = 20.dp)
            )
            Text(
                text = NumberFormat.getNumberInstance(Locale.getDefault()).format(viewModel.point),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori, fontSize = 18.sp
            )
        }

    }
}

@Composable
fun Top(navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row {
            Help(navController)
            Info(navController)
        }
        Point(navController)
    }
}

@Composable
fun MakeEgg(navController: NavController, characterState: MutableState<CharacterCode>){
    val viewModel : MainViewModel = viewModel()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isClicked = remember { mutableStateOf(false) }
        if (characterState.value == CharacterCode.CH444) {
            Text(text = "알을 생성하려면\n화면을 터치해주세요.", textAlign = TextAlign.Center, lineHeight = 50.sp,
                fontFamily = dalmoori, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { characterState.value = CharacterCode.CH003 }
                )
            )
        } else if (characterState.value != CharacterCode.CH444){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(isClicked.value){
                    Text(text = "성장을 위해\n화면을 터치해주세요.", textAlign = TextAlign.Center, lineHeight = 50.sp,
                        fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                characterState.value = CharacterCode.CH100
                                isClicked.value = false
                            }
                        )
                    )
                }
                else{
                    Text(text = " \n ", lineHeight = 50.sp, fontSize = 20.sp,)
                }
                Image(painter = painterResource(characterState.value.code), contentDescription = null,
                    modifier = Modifier
                        .height(250.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { isClicked.value = true }
                        )
                )
            }
        }
    }
}

@Composable
fun Btn(navController: NavController, characterState: MutableState<CharacterCode>){
    val viewModel : MainViewModel = viewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            val btn2Bg = painterResource(R.drawable.btn_2_bg)
            Image(painter = btn2Bg, contentDescription = null,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { navController.navigate(AppNavItem.Collect.route + "/memberId") }
                    )
                    .width(150.dp)
            )
            Text(
                text = "몽집", textAlign = TextAlign.Center,
                fontFamily = dalmoori, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White
            )
        }
        if (characterState.value != CharacterCode.CH444){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                val btn2Bg = painterResource(R.drawable.btn_2_bg)
                Image(painter = btn2Bg, contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { navController.navigate(AppNavItem.Condition.route + "/characterId") }
                        )
                        .width(150.dp)
                )
                Text(
                    text = "지수", textAlign = TextAlign.Center,
                    fontFamily = dalmoori, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
            }
        }
    }
}

@Composable
fun MainUI(
    navController: NavController,
    viewModel: MainViewModel
) {
    // 배경
    val findBgCode = viewModel.background
    val bgCode = BackgroundCode.valueOf(findBgCode)
    val bg = painterResource(bgCode.code)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight())

    // state 는 모두 공통 코드화 시켜야 함
    val characterState = remember { mutableStateOf(CharacterCode.CH444) }

    Top(navController)
    MakeEgg(navController, characterState)
    Btn(navController, characterState)
}

@Preview(showBackground = false)
@Composable
fun InfoPreview() {
    val navController = rememberNavController()
    val viewModel : MainViewModel = viewModel()
    PaymongTheme {
        MainUI(navController, viewModel)
    }
}