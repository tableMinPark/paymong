package com.paymong.ui.watch.activity

import android.graphics.Insets.add
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
//import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageRequest
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.TrainingViewModel
import com.paymong.domain.watch.battle.BattleFindViewModel
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize


@Composable
fun TrainingActive(
    navController: NavHostController
) {
    val viewModel: TrainingViewModel = viewModel()
    TrainingActiveUI(navController, viewModel)
}

@Composable
fun TrainingActiveUI(
    navController: NavHostController,
    viewModel: TrainingViewModel
) {

    val img = painterResource(R.drawable.training_bg)


    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight().clickable { viewModel.screenClick() {
                navController.navigate(WatchNavItem.Activity.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop =true
                }
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f)
        ) {
            Text(text = String.format("%02d:%02d", viewModel.second, viewModel.nanoSecond/10000000 ),
                fontFamily = dalmoori,
                fontSize = 20.sp)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top=10.dp)
        ) {
            Text(text = String.format("%d", viewModel.count),
                fontFamily = dalmoori,
                fontSize = 30.sp)
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top=10.dp, bottom = 10.dp)
        ) {
            val viewModel : MainInfoViewModel = viewModel()
            var findCode = viewModel.characterCode
            var chCode = CharacterCode.valueOf(findCode)
            val chA = painterResource(chCode.code)
            Image(painter = chA, contentDescription = null, modifier = Modifier.width(100.dp))
            MessageContents()
        }



        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (viewModel.isTrainingEnd) {
                Text(text = "훈련 종료",
                    fontFamily = dalmoori,
                    fontSize = 12.sp)
            } else {
                Text(text = "터치해서 훈련하기",
                    fontFamily = dalmoori,
                    fontSize = 12.sp)
            }
        }

    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun TrainingPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        TrainingActive(navController)
    }
}


@ExperimentalCoilApi
@Composable
fun MessageContents(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder(context))
            } else {
                add(GifDecoder())
            }
        }
        .build()
    Image(
        painter = rememberImagePainter(
            imageLoader = imageLoader,
            data = R.drawable.wat,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}

