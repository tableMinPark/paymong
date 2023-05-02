package com.paymong.ui.app.login

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.games.AuthenticationResult
import com.google.android.gms.games.GamesSignInClient
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.Player
import com.google.android.gms.tasks.Task
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.login.LoginViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun Login(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    LoginUI(navController, viewModel)
}

@Composable
fun LoginUI(
    navController: NavController,
    viewModel: LoginViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val context = LocalContext.current as Activity
            Button(
                onClick = {
                    var isLogin = true

                    /*
                        로그인 관련 로직 함수 호출
                    */



                    // 로그인 인증 완료 시 메인화면으로 이동
                    if (isLogin) {
                        navController.navigate(AppNavItem.Main.route){
                            // 백스택 비우기
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            // 스택 첫 화면 메인화면으로 변경
                            navController.graph.setStartDestination(AppNavItem.Main.route)
                            launchSingleTop =true
                        }
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
fun LoginPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        Login(navController)
    }
}