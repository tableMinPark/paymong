package com.paymong.ui.watch.battle

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.ToastMessage
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import com.paymong.ui.watch.landing.MainBackgroundGif


@Composable
fun BattleWait(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var fontSize = 0


    if (screenWidthDp < 200) {
        fontSize = 12


    }
    else {
        fontSize = 15

    }
    // 매칭 여부 확인해서 화면 이동
    if (battleViewModel.matchingState == MatchingCode.FOUND){
        navController.navigate(WatchNavItem.BattleFind.route) {
            popUpTo(0)
        }
        battleViewModel.battleFind()
    } else if (battleViewModel.matchingState == MatchingCode.NOT_FOUND){
        navController.navigate(WatchNavItem.BattleLanding.route){
            popUpTo(0)
        }
        battleViewModel.battleFindFail()
        Toast.makeText(LocalContext.current, ToastMessage.BATTLE_NOT_MATCHING.message, Toast.LENGTH_LONG).show()
    }

    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "...배틀을 찾는 중...",
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = fontSize.sp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
    LoadingGif()

}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleWaitPreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleWait(navController, viewModel)
    }
}


@ExperimentalCoilApi
@Composable
fun LoadingGif(

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
            data = R.drawable.loading,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}