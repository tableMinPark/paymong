package com.paymong.ui.watch.landing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.paymong.common.code.LandingCode
import com.paymong.domain.watch.WatchLandingViewModel
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.Logo

@Composable
fun Landing(
    watchLandingViewModel : WatchLandingViewModel
){
    Background()

    LaunchedEffect(key1 = true){
        // 로그인 확인
        watchLandingViewModel.login()
    }

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val fontSize = if (screenWidthDp < 200) 12 else 15

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (watchLandingViewModel.landingCode == LandingCode.NOT_CONFIG) {
                    watchLandingViewModel.openAppOnPhone()
                } else if (watchLandingViewModel.landingCode == LandingCode.NOT_INSTALL) {
                    watchLandingViewModel.openAppInStoreOnPhone()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (watchLandingViewModel.landingCode) {
            // 랜딩 중
            LandingCode.LOADING -> {
                Logo()
            }
            // 랜딩 실패 (초기 설정이 되지 않은 경우)
            LandingCode.NOT_CONFIG -> {
                Text(
                    text = "초기 설정이 필요합니다.\n\n터치해서\n\n모바일 앱에서 초기 설정 하기",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = dalmoori,
                    color = PayMongRed200,
                    fontSize = fontSize.sp,
                )
            }
            // 랜딩 실패 (설치가 되지 않은 경우)
            LandingCode.NOT_INSTALL -> {
                Text(
                    text = "모바일 앱 설치 후\n설정이 필요합니다.\n\n터치해서\n\n모바일 앱 설치 하기",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = dalmoori,
                    color = PayMongRed200,
                    fontSize = fontSize.sp,
                )
            }
            // 랜딩 실패 (이 외의 다른 경우)
            LandingCode.FAIL -> {
                Text(
                    text = "앱을 실행할 수 없습니다.\n\n앱을 재실행 해 주세요.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = dalmoori,
                    color = PayMongRed200,
                    fontSize = fontSize.sp,
                )
            }
            else -> {}
        }
    }
}
