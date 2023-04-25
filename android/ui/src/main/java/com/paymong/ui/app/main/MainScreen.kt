package com.paymong.ui.app.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.ui.theme.PaymongTheme

@Composable
fun Main(navController: NavController) {
    // state 는 모두 공통 코드화 시켜야함
    val characterState = remember { mutableStateOf(CharacterCode.CH100) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    /*
                        도움말 모달 호출
                    */
                },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "?", textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    /*
                        캐릭터 상태 모달 호출
                    */
                },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "i", textAlign = TextAlign.Center)
            }
            Button(
                onClick = {
                    /*
                        포인트 목록 모달 호출
                    */
                },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "P 10,000", textAlign = TextAlign.Center)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (characterState.value != CharacterCode.CH100){
                Text(text = "대충 알 생성되서 캐릭터 서있는 거", textAlign = TextAlign.Center)
            }
            else {
                Button(
                    onClick = {
                        /*
                            알 생성 로직 호출
                        */
                        characterState.value = CharacterCode.CH100
                    },
                    modifier = Modifier.size(60.dp)
                ) {
                    Text(text = "알 생성", textAlign = TextAlign.Center)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(AppNavItem.Ending.route) },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "도감", textAlign = TextAlign.Center)
            }

            if (characterState.value != CharacterCode.CH100){
                Button(
                    onClick = {
                        /*
                            지수 모달 호출
                        */
                    },
                    modifier = Modifier.size(60.dp)
                ) {
                    Text(text = "지수", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Main(navController)
    }
}