package com.paymong.ui.watch.main

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.paymong.common.code.AnimationCode
import com.paymong.domain.watch.feed.FeedBuyListViewModel
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

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
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("캐릭터 코드 : %s %s", animationState.value, viewModel.characterCode), textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("맵 코드 : %d", viewModel.poopCount), textAlign = TextAlign.Center)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = String.format("똥 개수 : %d", viewModel.poopCount), textAlign = TextAlign.Center)
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