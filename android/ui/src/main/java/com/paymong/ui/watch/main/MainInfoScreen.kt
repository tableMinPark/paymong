package com.paymong.ui.watch.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.CharacterCode
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.domain.watch.main.MainViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun MainInfo(
    animationState: MutableState<AnimationCode>
) {
    val viewModel: MainInfoViewModel = viewModel()
    MainInfoUI(animationState, viewModel)
}

@Composable
fun MainInfoUI(
    animationState: MutableState<AnimationCode>,
    viewModel: MainInfoViewModel
) {
    val mapViewModel : MainViewModel = viewModel()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var characterSize = 0


    if (screenWidthDp < 200) {
        characterSize = 120


    }
    else {
        characterSize = 150

    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("맵 코드 : %s", mapViewModel.background), textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("똥 개수 : %d", viewModel.poopCount), textAlign = TextAlign.Center)
        }
        Row(
                horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
        ) {

        val findCode = viewModel.characterCode
        val chCode = CharacterCode.valueOf(findCode)
        val character = painterResource(chCode.code)
        Image(painter = character, contentDescription = null, modifier = Modifier.width(characterSize.dp))
//            Text(text = String.format("캐릭터 코드 : %s %s", animationState.value, viewModel.characterCode), textAlign = TextAlign.Center)
    }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainInfoPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    PaymongTheme {
        MainInfo(animationState)
    }
}