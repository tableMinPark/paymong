package com.paymong.ui.app.component

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.R
import com.paymong.common.code.MongStateCode
import com.paymong.domain.app.AppViewModel

@ExperimentalCoilApi
@Composable
fun CharacterGif(appViewModel: AppViewModel, size: Int) {
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
            data = appViewModel.mong.mongCode.gifCode,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier.clickable {
            appViewModel.stroke()
            Handler(Looper.getMainLooper()).postDelayed({
                appViewModel.isHappy = false
            }, 2000)
        }.size(size.dp)
    )
}

@ExperimentalCoilApi
@Composable
fun EmotionGif(appViewModel: AppViewModel, paddingTop:Int, paddingRight:Int, paddingBottom: Int, size:Int) {
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

    var imgData = when(appViewModel.stateCode){
        MongStateCode.CD001 -> R.drawable.sad
        MongStateCode.CD002 -> R.drawable.sleeping
        MongStateCode.CD003 -> R.drawable.depressed
        MongStateCode.CD004 -> R.drawable.sulky

        else -> R.drawable.smile
    }

    if(appViewModel.isHappy){
        imgData = R.drawable.happy
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
        modifier = Modifier.padding(top = paddingTop.dp, end = paddingRight.dp, bottom= paddingBottom.dp).size(size.dp)
    )
}

fun showToast(context: Context, toastMessage: String) {
    Toast.makeText(
        context,
        toastMessage,
        Toast.LENGTH_SHORT
    ).show()
}