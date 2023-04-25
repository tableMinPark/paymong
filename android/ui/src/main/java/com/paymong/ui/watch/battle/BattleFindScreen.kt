package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.battle.BattleFindViewModel
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme

@Composable
fun BattleFind(
    navController: NavHostController
) {
    val bg = painterResource(R.drawable.main_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)

    Button(
        onClick = { navController.navigate(WatchNavItem.BattleActive.route) },
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
    }

    val viewModel : BattleFindViewModel = viewModel()
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(text = "A", textAlign = TextAlign.Center)
            var findCode = viewModel.characterIdForA
            var chCode = CharacterCode.valueOf(findCode)
            var chA = painterResource(chCode.code)
            Image(painter = chA, contentDescription = null, modifier = Modifier.width(100.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(text = "VS", textAlign = TextAlign.Center)
            val battle = painterResource(R.drawable.battle)
            Image(painter = battle, contentDescription = null)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
//            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(text = "B", textAlign = TextAlign.Center)
            var findCode = viewModel.characterIdForB
            var chCode = CharacterCode.valueOf(findCode)
            var chB = painterResource(chCode.code)
            Image(painter = chB, contentDescription = null, modifier = Modifier.width(100.dp))
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleFindPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleFind(navController)
    }
}