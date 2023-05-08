package com.paymong.ui.watch.landing

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.LandingCode
import com.paymong.domain.watch.WatchLandingViewModel
import com.paymong.ui.theme.PayMongRed200
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import kotlinx.coroutines.delay

@Composable
fun Landing(
    navController: NavController,
    watchLandingViewModel : WatchLandingViewModel
){
    LaunchedEffect(key1 = true){
        watchLandingViewModel.loginCheck()
        delay(2000)

        // 로그인 성공 (리프레시 있음)
        if(watchLandingViewModel.loginState == LandingCode.LOGIN_SUCCESS) {
            Log.e("Landing", "로그인 성공")
            watchLandingViewModel.loginState = LandingCode.LOGIN
            navController.navigate(WatchNavItem.Main.route){
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                // 스택 첫 화면 메인화면으로 변경
                navController.graph.setStartDestination(WatchNavItem.Main.route)
                launchSingleTop =true
            }
        } 
        // 로그인 실패 (리프레시 없음)
        else if (watchLandingViewModel.loginState == LandingCode.LOGIN_FAIL){
            Log.e("Landing", "로그인 실패")
            watchLandingViewModel.loginState = LandingCode.LOADING
            // 모바일 앱 설치 여부 확인
            watchLandingViewModel.installCheck()
            
            // playerId 가 있거나 모바일 앱으로부터 playerId를 받아온 경우
            if (watchLandingViewModel.landingCode == LandingCode.VALID){
                watchLandingViewModel.login()
            }
        }
    }


    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = 0
    if (screenWidthDp < 200)
        fontSize = 12
    else
        fontSize = 15

    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (watchLandingViewModel.landingCode == LandingCode.NOT_INSTALL) {
                    watchLandingViewModel.openAppInStoreOnPhone()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (watchLandingViewModel.landingCode == LandingCode.NOT_INSTALL) {
            Text(
                text = "모바일 앱 설치 후\n설정이 필요합니다.\n\n터치해서\n모바일 앱 설치하기",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = dalmoori,
                color = PayMongRed200,
                fontSize = fontSize.sp,
            )
        } else {
            Logo()
        }
    }
}

@Composable
fun Logo(){
    val logo = painterResource(R.drawable.watch_logo)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    if (screenWidthDp < 200) {
        Image(painter = logo, contentDescription = null, modifier = Modifier.size(120.dp))
    }
    else {
        Image(painter = logo, contentDescription = null, modifier = Modifier.size(150.dp))
    }
}

@ExperimentalCoilApi
@Composable
fun MainBackgroundGif(

) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder(context))
            } else {
                add(GifDecoder())
            }
        }
        .build()
    Image(
        painter = rememberImagePainter(
            imageLoader = imageLoader,
            data = R.drawable.main_bg_gif,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun LandingPreview(){
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
//        Landing(navController)
    }
}