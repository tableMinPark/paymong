package com.paymong.ui.watch.landing

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.ui.theme.PaymongTheme

@Composable
fun Landing(navController: NavController){
    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    MainBackgroundGif()
    Logo()

    //2초 후 메인화면으로 전환
    Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate(WatchNavItem.Main.route)
    },2000)
}

@Composable
fun Logo(modifier: Modifier = Modifier){
    val logo = painterResource(R.drawable.watch_logo)

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp



    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        if (screenWidthDp < 200) {
            Image(painter = logo, contentDescription = null, modifier = modifier.size(120.dp))
        }
        else {
            Image(painter = logo, contentDescription = null, modifier = modifier.size(150.dp))
        }

    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun LandingPreview(){
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        Landing(navController)
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
