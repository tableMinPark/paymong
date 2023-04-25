package com.paymong.ui.watch.battle

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.domain.watch.battle.BattleActiveViewModel
import com.paymong.domain.watch.battle.BattleFindViewModel
import com.paymong.ui.theme.*

@Composable
fun BattleActive(
    navController: NavHostController
) {
    val bg = painterResource(R.drawable.battle_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)

    val defence = painterResource(R.drawable.defence)
    val attack = painterResource(R.drawable.attack)

    val viewModel : BattleActiveViewModel = viewModel()
    var cnt by remember { mutableStateOf(viewModel.count) }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (viewModel.order == "A") {
                Image(
                    painter = attack, contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            } else {
                Image(
                    painter = defence, contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }
            var findCode = viewModel.characterIdForA
            var chCode = CharacterCode.valueOf(findCode)
            var chA = painterResource(chCode.code)
            Image(painter = chA, contentDescription = null, modifier = Modifier.width(100.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            //게이지 바
            var damA = viewModel.damageA
            Box(modifier = Modifier
                .width(60.dp)
                .height(20.dp)
                .clip(CircleShape)
                .background(Color.White)){
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = damA)
                    .clip(CircleShape)
                    .background(color = PayMongRed)
                    .padding(8.dp)) {}
            }
            Text(text=String.format("%d/%d",cnt,viewModel.totalTurn),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 10.dp))
            //게이지 바
            var damB = viewModel.damageB
            Box(modifier = Modifier
                .width(60.dp)
                .height(20.dp)
                .clip(CircleShape)
                .background(Color.White)){
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = damB)
                    .clip(CircleShape)
                    .background(color = PayMongRed)
                    .padding(8.dp)) {}
            }
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(text = "B", textAlign = TextAlign.Center)
            var findCode = viewModel.characterIdForB
            var chCode = CharacterCode.valueOf(findCode)
            var chB = painterResource(chCode.code)
            Image(painter = chB, contentDescription = null, modifier = Modifier.width(100.dp))

            if (viewModel.order == "A") {
                Image(
                    painter = defence, contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            } else {
                Image(
                    painter = attack, contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }
        }
    }

//    var cnt by remember { mutableStateOf(viewModel.count) }
    Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate(WatchNavItem.BattleSelectBefore.route)
    },2000)
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleActivePreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleActive(navController)
    }
}