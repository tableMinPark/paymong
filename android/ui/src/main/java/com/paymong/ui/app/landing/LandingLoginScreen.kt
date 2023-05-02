package com.paymong.ui.app.landing

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.paymong.common.api.Apis
import com.paymong.common.dto.request.Register
import com.paymong.common.dto.request.ResponseMsg
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.landing.LandingViewModel
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.theme.PaymongTheme
import javax.security.auth.callback.Callback


@Composable
fun LandingLogin(navController: NavController) {
    val viewModel: LandingViewModel = viewModel()
    LandingLoginUI(navController, viewModel)
}

@Composable
fun LandingLoginUI(
    navController: NavController,
    viewModel: LandingViewModel
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
            val api = Apis.create()

            val google = painterResource(R.drawable.google_login)
            Image(painter = google, contentDescription = null,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null, // 애니메이션 제거
                    onClick = {
                        val gamesSignInClient: GamesSignInClient =
                            PlayGames.getGamesSignInClient(context)

                        gamesSignInClient.isAuthenticated.addOnCompleteListener { isAuthenticatedTask: Task<AuthenticationResult> ->
                            val isAuthenticated = isAuthenticatedTask.isSuccessful &&
                                    isAuthenticatedTask.result.isAuthenticated
                            if (isAuthenticated) {
                                PlayGames.getPlayersClient(context).currentPlayer.addOnCompleteListener { mTask: Task<Player?>? ->
                                    val ID = mTask?.result?.playerId
                                    Log.d("id","$ID")
                                    val sharedPref = context.getSharedPreferences("loginId",Context.MODE_PRIVATE)
                                    with (sharedPref.edit()) {
                                        putString("loginId", ID)
                                        apply()
                                    }
                                    api.register(ID.toString())
                                }
                                navController.navigate(AppNavItem.Main.route){
                                    // 백스택 비우기
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                    // 스택 첫 화면 메인화면으로 변경
                                    navController.graph.setStartDestination(AppNavItem.Main.route)
                                    launchSingleTop =true
                                }
                            } else {
                                gamesSignInClient.signIn()
                            }
                        }
                    }
                ))
        }
    }
}
@Composable
fun SaveData(loginId:String){
    val context = LocalContext.current as Activity
    val sharedPref = context.getPreferences(Context.MODE_PRIVATE) ?: return
    with (sharedPref.edit()) {
        putString("loginId", loginId)
        apply()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LandingLoginPreview() {
    val navController = rememberNavController()
    PaymongTheme {
    }
}