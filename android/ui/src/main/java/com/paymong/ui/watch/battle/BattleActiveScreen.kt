package com.paymong.ui.watch.battle

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MatchingCode
import com.paymong.common.dto.response.BattleMessageResDto
import com.paymong.common.entity.BattleActiveEntity
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.*

@Composable
fun BattleActive(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {
    if (battleViewModel.matchingState == MatchingCode.SELECT_BEFORE){
        navController.navigate(WatchNavItem.BattleSelectBefore.route) {
            popUpTo(0)
            launchSingleTop =true
        }
        battleViewModel.battleSelectBefore()
    } else if (battleViewModel.matchingState == MatchingCode.END){
        navController.navigate(WatchNavItem.BattleEnd.route) {
            popUpTo(0)
            launchSingleTop =true
        }
        battleViewModel.battleEnd()
    } else if (battleViewModel.matchingState == MatchingCode.ACTIVE_RESULT){
        Log.d("battle", "싸움 애니메이션 ON")
        battleViewModel.matchingState = MatchingCode.SELECT_BEFORE
    }


    val bg = painterResource(R.drawable.battle_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)

    val defence = painterResource(R.drawable.defence)
    val attack = painterResource(R.drawable.attack)

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (battleViewModel.battleActiveEntity.order == "A") {
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
            var findCode = battleViewModel.characterCodeA
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
            var damA = battleViewModel.battleActiveEntity.damageA.toFloat()
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
            Text(text=String.format("%d/%d", battleViewModel.battleActiveEntity.nowTurn, battleViewModel.TOTAL_TURN),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 10.dp))
            //게이지 바
            var damB = battleViewModel.battleActiveEntity.damageB.toFloat()
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
            var findCode = battleViewModel.characterCodeB
            var chCode = CharacterCode.valueOf(findCode)
            var chB = painterResource(chCode.code)
            Image(painter = chB, contentDescription = null, modifier = Modifier.width(100.dp))

            if (battleViewModel.battleActiveEntity.order == "A") {
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
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleActivePreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleActive(navController, viewModel)
    }
}