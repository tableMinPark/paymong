package com.paymong.ui.watch.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.paymong.common.R
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.CharacterCode
import com.paymong.domain.watch.main.MainInfoViewModel

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

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var characterSize = 0
    var poopSize = 0



    if (screenWidthDp < 200) {
        characterSize = 100
        poopSize = 25

    }
    else {
        characterSize = 120
        poopSize = 35
    }



    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = String.format("맵 코드 : %s", mapViewModel.mapCode), textAlign = TextAlign.Center)
//        }
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = String.format("똥 개수 : %d", viewModel.poopCount), textAlign = TextAlign.Center)
//        }

        Box(   modifier = Modifier.fillMaxWidth().fillMaxHeight(1f).padding(top=60.dp) ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {


                val character = painterResource(viewModel.mong.mongCode.resourceCode)
//                val character = painterResource(CharacterCode.CH302.resourceCode)
                Image(
                    painter = character,
                    contentDescription = null,
                    modifier = Modifier.width(characterSize.dp)
                )
//            Text(text = String.format("캐릭터 코드 : %s %s", animationState.value, viewModel.characterCode), textAlign = TextAlign.Center)
            }

            val poopCount = viewModel.poopCount

            if (poopCount == 1) {
                Poops(0, 100, 75, 0, poopSize)
            }
            else if (poopCount == 2) {
                Poops(0, 100, 75, 0, poopSize)
                Poops(90, 0, 80, 0, poopSize)
            }
            else if (poopCount == 3) {
                Poops(0, 100, 75, 0, poopSize)
                Poops(90, 0, 80, 0, poopSize)
                Poops(30, 0, 92, 0, poopSize)
            }
            else if (poopCount == 4 ) {
                Poops(0, 100, 75, 0, poopSize)
                Poops(90, 0, 80, 0, poopSize)
                Poops(30, 0, 92, 0, poopSize)
                Poops(0, 60, 95, 0, poopSize)
            }





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


@Composable
fun Poops(start:Int, end:Int, top:Int, bottom:Int, poopSize:Int ){
    val poops = painterResource(R.drawable.poops)

    Box(
//        horizontalArrangement = Arrangement.Center,
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
            .padding(start = start.dp, end = end.dp, top = top.dp, bottom = bottom.dp)
    ) {


        Image(
            painter = poops,
            contentDescription = null,
            modifier = Modifier.size(poopSize.dp)
        )
    }
}