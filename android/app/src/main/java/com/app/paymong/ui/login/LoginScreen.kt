package com.app.paymong.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.paymong.navigation.NavItem
import com.app.paymong.ui.theme.PaymongTheme

@Composable
fun Login(navController: NavController) {
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
                    var isLogin = true

                    /*
                        로그인 관련 로직 함수 호출
                    */

                    // 로그인 인증 완료 시 메인화면으로 이동
                    if (isLogin) {
                        navController.navigate(NavItem.Main.route)
                    }
                },
                modifier = Modifier.size(60.dp)
            ) {
                Text(text = "구글 로그인", textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConditionPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Login(navController)
    }
}