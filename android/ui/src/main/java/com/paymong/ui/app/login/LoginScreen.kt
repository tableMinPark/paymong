package com.paymong.ui.app.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.paymong.common.R
import com.paymong.common.code.LoginCode
import com.paymong.common.code.ToastMessage
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.LoginViewModel
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.theme.PaymongTheme
import java.util.concurrent.TimeUnit

@Composable
fun Login(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    BgGif()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val logo = painterResource(R.drawable.app_logo)
            Image(painter = logo, contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .padding(80.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            val context = LocalContext.current as Activity
            val google = painterResource(R.drawable.google_login)
            val gamesSignInClient = PlayGames.getGamesSignInClient(context)

            Image(painter = google, contentDescription = null,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null, // 애니메이션 제거
                    onClick = { login(context, navController, gamesSignInClient, loginViewModel) }
                )
            )
        }
    }
}

fun login(
    context: Activity,
    navController: NavController,
    gamesSignInClient : GamesSignInClient,
    loginViewModel: LoginViewModel
) {

    // 로그인 시도
    gamesSignInClient.signIn()
    // 로그인 리스너
    gamesSignInClient.isAuthenticated.addOnCompleteListener { isAuthenticatedTask: Task<AuthenticationResult> ->
        loginViewModel.isAuthenticated = isAuthenticatedTask.isSuccessful && isAuthenticatedTask.result.isAuthenticated
        // 로그인 성공
        if (loginViewModel.isAuthenticated) {
            PlayGames.getPlayersClient(context).currentPlayer.addOnCompleteListener { mTask: Task<Player?>? ->
                val playerId = mTask?.result?.playerId.toString()

                val isSuccess = loginViewModel.login(playerId)
                // 로그인 성공
                if (isSuccess) {
                    navController.navigate(AppNavItem.Main.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        navController.graph.setStartDestination(AppNavItem.Main.route)
                        launchSingleTop = true
                    }
                }
                // 로그인 실패
                else {
                    Toast.makeText(
                        context,
                        ToastMessage.LOGIN_FAIL.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        // 계정을 찾을 수 없음
        else {
            Toast.makeText(
                context,
                ToastMessage.LOGIN_ACCOUNT_NOT_FOUND.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LandingLoginPreview() {
    val navController = rememberNavController()
    PaymongTheme {
    }
}