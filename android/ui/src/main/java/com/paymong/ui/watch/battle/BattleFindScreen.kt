package com.paymong.ui.watch.battle

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MatchingCode
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun BattleFind(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var characterSize = 0

    if (screenWidthDp < 200) {
        characterSize = 80
    }
    else {
        characterSize = 100
    }

    if (battleViewModel.matchingState == MatchingCode.ACTIVE){
        navController.navigate(WatchNavItem.BattleActive.route) {
            popUpTo(0)
            launchSingleTop =true
        }
        battleViewModel.battleActive()
    }

    val bg = painterResource(R.drawable.battle_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    BattleBackgroundGif()
    Button(
        onClick = { navController.navigate(WatchNavItem.BattleActive.route) },
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
//            battleViewModel.battleEntity?.let { Text(text = it.battleRoomId, textAlign = TextAlign.Center) }
            var findCode = battleViewModel.characterCodeA
            var chCode = CharacterCode.valueOf(findCode)
            var chA = painterResource(chCode.code)
            Image(painter = chA, contentDescription = null, modifier = Modifier.width(characterSize.dp).height(characterSize.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            val battle = painterResource(R.drawable.battle)
            Image(painter = battle, contentDescription = null)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
//            modifier = Modifier.fillMaxWidth()
        ) {
//            battleViewModel.battleEntity?.let { Text(text = it.battleRoomId, textAlign = TextAlign.Center) }
            var findCode = battleViewModel.characterCodeB
            var chCode = CharacterCode.valueOf(findCode)
            var chB = painterResource(chCode.code)
            Image(painter = chB, contentDescription = null, modifier = Modifier.width(characterSize.dp).height(characterSize.dp))
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleFindPreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleFind(navController, viewModel)
    }
}

@ExperimentalCoilApi
@Composable
fun BattleBackgroundGif(

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
            data = R.drawable.battle_bg_gif,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}

