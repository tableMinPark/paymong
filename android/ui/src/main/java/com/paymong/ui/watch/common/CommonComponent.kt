package com.paymong.ui.watch.common

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.paymong.common.code.MongStateCode
import com.paymong.common.code.ToastMessage
import com.paymong.domain.watch.WatchViewModel

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

@ExperimentalCoilApi
@Composable
fun LoadingGif() {
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
    )
}

@ExperimentalCoilApi
@Composable
fun WalkingBackgroundGif() {
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
            data = R.drawable.walking_bg_gif,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
    )
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

@ExperimentalCoilApi
@Composable
fun AttackGif() {
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
            data = R.drawable.attack_motion,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}

@ExperimentalCoilApi
@Composable
fun DefenceGif() {
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
            data = R.drawable.defence_motion,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}

@ExperimentalCoilApi
@Composable
fun CharacterGif(mainViewModel:WatchViewModel) {
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
            data = mainViewModel.mong.mongCode.gifCode,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                if(mainViewModel.stateCode!=MongStateCode.CD002) {
                    mainViewModel.stroke()
                    Handler(Looper.getMainLooper()).postDelayed({
                        mainViewModel.isHappy = false
                    }, 2000)
                }
            }
        )
    )
}

@ExperimentalCoilApi
@Composable
fun EmotionGif(mainViewModel:WatchViewModel, paddingTop:Int, paddingRight:Int, paddingBottom: Int, size:Int, stateCode:MongStateCode) {
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

    var imgData = when(mainViewModel.stateCode){
        MongStateCode.CD001 -> R.drawable.sad
        MongStateCode.CD002 -> R.drawable.sleeping
        MongStateCode.CD003 -> R.drawable.depressed
        MongStateCode.CD004 -> R.drawable.sulky

        else -> R.drawable.smile
    }

    if(mainViewModel.isHappy){
        imgData = R.drawable.happy
    }

    if(mainViewModel.eating){
        imgData = R.drawable.eating
    }

    Image(
        painter = rememberImagePainter(
            imageLoader = imageLoader,
            data = imgData,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
            .padding(top = paddingTop.dp, end = paddingRight.dp, bottom = paddingBottom.dp)
            .size(size.dp)
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

fun showToast(context : Context, toastMessage: ToastMessage) {
    Toast.makeText(
        context,
        toastMessage.message,
        Toast.LENGTH_SHORT
    ).show()
}
