package com.paymong.ui.app.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.LandingCode
import com.paymong.domain.app.AppLandinglViewModel
import com.paymong.ui.app.common.BgGif
import com.paymong.ui.app.common.LoadingBar
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.dalmoori

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Landing(
    appLandingViewModel : AppLandinglViewModel
) {
    LaunchedEffect(key1 = true){
        // 로그인 확인
        appLandingViewModel.googlePlayLogin()
    }
    BgGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val fontSize = if (screenWidthDp < 200) 12 else 15

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
        when(appLandingViewModel.landingCode) {
            LandingCode.LOADING -> {
                LoadingBar()
            }
            LandingCode.LOGIN_FAIL -> {
                // 구글 로그인 실패
                // 구글 로그인 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    val google = painterResource(R.drawable.google_login)
                    Image(painter = google, contentDescription = null,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { appLandingViewModel.login() }
                        )
                    )
                }
            }
            LandingCode.NOT_HAS_WEARABLE -> {
                // node_id가 없고 주변에 등록된 웨어러블 기기가 없음
                // 진입 불가 텍스트
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "등록 된 웨어러블 기기가 없습니다.\n\n웨어러블 기기 최초 등록 후\n\n앱을 이용할 수 있습니다.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = dalmoori,
                        color = PayMongRed200,
                        fontSize = fontSize.sp
                    )
                }
            }
            LandingCode.NOT_CONFIG -> {
                // node_id가 없고 주변에 등록된 웨어러블 기기가 있으며 설치가 됨
                // Wearable 기기와 연동 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val wearableConnect = painterResource(R.drawable.wearable_connect)
                    Image(painter = wearableConnect, contentDescription = null,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { appLandingViewModel.registerWearable() }
                        )
                    )
                }
            }
            LandingCode.NOT_INSTALL -> {
                // node_id가 없고 주변에 등록된 웨어러블 기기가 있으며 설치가 되지 않음
                // 터치해서 웨어러블 앱 설치 텍스트
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { appLandingViewModel.openPlayStoreOnWearDevicesWithoutApp() },
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "웨어러블 기기에 앱이 설치되지 않았습니다.\n\n터치하여 앱 설치하기",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = dalmoori,
                        color = PayMongRed200,
                        fontSize = fontSize.sp
                    )
                }
            }
            LandingCode.FAIL -> {
                // 그외의 경우
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "앱을 실행할 수 없습니다.\n\n앱을 재실행 해 주세요.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontFamily = dalmoori,
                        color = PayMongRed200,
                        fontSize = fontSize.sp
                    )
                }
            }
            else -> {}
        }
    }
}