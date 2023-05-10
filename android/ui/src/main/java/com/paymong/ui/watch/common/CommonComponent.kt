package com.paymong.ui.watch.common

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.R
import com.paymong.common.code.MapCode

@Composable
fun Background(
    isGif: Boolean = false,
    mapCode: MapCode = MapCode.MP000
) {
    val background = painterResource(mapCode.code)
    Image(painter = background, contentDescription = null, contentScale = ContentScale.Crop)
    if (isGif) MainBackgroundGif()
}

@ExperimentalCoilApi
@Composable
fun MainBackgroundGif() {
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