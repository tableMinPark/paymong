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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.MatchingCode
import com.paymong.domain.watch.BattleViewModel
import com.paymong.ui.theme.*
import android.os.Handler
import android.os.Looper
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.code.SoundCode
import com.paymong.domain.watch.SoundViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.watch.common.AttackGif
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.BattleBackgroundGif
import com.paymong.ui.watch.common.DefenceGif

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BattleActive(
    navController: NavHostController,
    watchViewModel: WatchViewModel,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel
) {
    Background(false)
    BattleBackgroundGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val characterSize = if (screenWidthDp < 200) 80 else 100
    val fontSize = if (screenWidthDp < 200) 60 else 70
    val barWidth = if (screenWidthDp < 200) 15 else 20
    val barHeight = if (screenWidthDp < 200) 20 else 24
    val attackDefenceSize = if (screenWidthDp < 200) 35 else 40
    val attackDefenceSizeSmall = if (screenWidthDp < 200) 25 else 30

    val defence = painterResource(R.drawable.defence)
    val attack = painterResource(R.drawable.attack)

    val playerResourceCodeA = painterResource(battleViewModel.playerCodeA.resourceCode)
    val playerResourceCodeB = painterResource(battleViewModel.playerCodeB.resourceCode)

    when (battleViewModel.matchingState) {
        MatchingCode.SELECT_BEFORE -> {
            navController.navigate(WatchNavItem.BattleSelectBefore.route) {
                popUpTo(0)
                launchSingleTop = true
            }
            battleViewModel.battleSelectBefore()
        }
        MatchingCode.END -> {
            navController.navigate(WatchNavItem.BattleEnd.route) {
                popUpTo(0)
                launchSingleTop = true
            }
            battleViewModel.battleEnd()
        }
        MatchingCode.ACTIVE_RESULT -> {
            Handler(Looper.getMainLooper()).postDelayed({
                Log.d("battle", "싸움 애니메이션 ON")

                battleViewModel.matchingState = MatchingCode.SELECT_BEFORE
            }, 4000)
        }
        else -> {}
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (battleViewModel.battleActive.order == "A") {
                if (battleViewModel.nextAttacker == "A") {
                    Image(
                        painter = defence, contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .size(attackDefenceSizeSmall.dp)
                    )

                } else {
                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .size(attackDefenceSizeSmall.dp)
                    )
                }
            } else {
                if (battleViewModel.nextAttacker == "A") {
                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .size(attackDefenceSizeSmall.dp)
                    )

                } else {
                    Image(
                        painter = defence, contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .size(attackDefenceSizeSmall.dp)
                    )
                }
            }
//
//
//            var findCode = ""
//            val chCode : CharacterCode
//            val player1: Painter
//
//            if (battleViewModel.battleActive.order == "A") {
//                findCode = battleViewModel.playerCodeB
//                chCode = CharacterCode.valueOf(findCode)
//                player1 = painterResource(chCode.resourceCode)
//
//            } else {
//                findCode = battleViewModel.playerCodeA
//                chCode = CharacterCode.valueOf(findCode)
//                player1 = painterResource(chCode.resourceCode)
//            }
//                Image(
//                    painter = chA,
//                    contentDescription = null,
//                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
//                )
            if (battleViewModel.matchingState == MatchingCode.ACTIVE_RESULT) {
                if (battleViewModel.battleActive.order == "A" && battleViewModel.battleActive.nowTurn % 2 != 0) {
                    if (battleViewModel.battleActive.damageB > 0) {
                        Box() {
                            Row() {
                                Image(
                                    painter = playerResourceCodeA,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row(modifier = Modifier.size(characterSize.dp)) {
                                soundViewModel.soundPlay(SoundCode.BATTLE_ATTACK)
                                AttackGif()
//                            Image(
//                                painter = attack,
//                                contentDescription = null,
//                                modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
//                            )
                            }
                        }

                    } else {

                        Box() {

                            Row() {
                                Image(
                                    painter = playerResourceCodeA,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row() {
                                Row(
                                    modifier = Modifier
                                        .size(characterSize.dp)
                                        .padding(start = 10.dp)
                                ) {
                                    soundViewModel.soundPlay(SoundCode.BATTLE_DEFENCE)
                                    DefenceGif()
                                }
                            }
                        }
                    }
                } else if (battleViewModel.battleActive.order == "B" && battleViewModel.battleActive.nowTurn % 2 == 0) {
                    if (battleViewModel.battleActive.damageA > 0) {
                        Box() {
                            Row() {
                                Image(
                                    painter = playerResourceCodeA,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row(modifier = Modifier.size(characterSize.dp)) {
                                soundViewModel.soundPlay(SoundCode.BATTLE_ATTACK)
                                AttackGif()
//                                Image(
//                                    painter = attack,
//                                    contentDescription = null,
//                                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
//                                )
                            }
                        }

                    } else {

                        Box() {
                            Row() {
                                Image(
                                    painter = playerResourceCodeA,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .size(characterSize.dp)
                                    .padding(start = 10.dp)
                            ) {
                                soundViewModel.soundPlay(SoundCode.BATTLE_DEFENCE)
                                DefenceGif()
                            }
                        }
                    }

                } else {
                    Box() {
                        Row() {
                            Image(
                                painter = playerResourceCodeA,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(characterSize.dp)
                                    .height(characterSize.dp)
                            )
                        }
                    }
                }

            } else {
                Box() {
                    Row() {
                        Image(
                            painter = playerResourceCodeA,
                            contentDescription = null,
                            modifier = Modifier
                                .width(characterSize.dp)
                                .height(characterSize.dp)
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            //게이지 바
            var damA = battleViewModel.battleActive.damageA.toFloat()
            var healA = battleViewModel.battleActive.healthA.toFloat()
            Box(
                modifier = Modifier
                    .width(barWidth.dp)
                    .height(barHeight.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = healA / 500)
                        .clip(CircleShape)
                        .background(color = PayMongRed)
                        .padding(8.dp)
                ) {}
            }
            Text(
                text = String.format(
                    "%d/%d",
                    battleViewModel.battleActive.nowTurn,
                    battleViewModel.totalTurn
                ),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = fontSize.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            //게이지 바
            var damB = battleViewModel.battleActive.damageB.toFloat()
            var healB = battleViewModel.battleActive.healthB.toFloat()
            Box(
                modifier = Modifier
                    .width(barWidth.dp)
                    .height(barHeight.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = healB / 500)
                        .clip(CircleShape)
                        .background(color = PayMongRed)
                        .padding(8.dp)
                ) {}
            }
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
//            Text(text = "B", textAlign = TextAlign.Center)
            // player2 :: 아래쪽
//            var findCode = ""
//            val chCode : CharacterCode
//            val player2: Painter
//
//            if (battleViewModel.battleActive.order == "A") {
//                findCode = battleViewModel.playerCodeA
//                chCode = CharacterCode.valueOf(findCode)
//                player2 = painterResource(chCode.resourceCode)
//
//            } else {
//                findCode = battleViewModel.playerCodeB
//                chCode = CharacterCode.valueOf(findCode)
//                player2 = painterResource(chCode.resourceCode)
//            }

//            Image(painter = player2, contentDescription = null, modifier = Modifier.width(characterSize.dp).height(characterSize.dp))

            // ------------------------------------------------------------------------------------------------------------------------------------------------


            if (battleViewModel.matchingState == MatchingCode.ACTIVE_RESULT) {
                if (battleViewModel.battleActive.order == "B" && battleViewModel.battleActive.nowTurn % 2 != 0) {
                    if (battleViewModel.battleActive.damageB > 0) {

                        Box() {
                            Row() {
                                Image(
                                    painter = playerResourceCodeB,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row(modifier = Modifier.size(characterSize.dp)) {
                                soundViewModel.soundPlay(SoundCode.BATTLE_ATTACK)
                                AttackGif()
//                                Image(
//                                    painter = attack,
//                                    contentDescription = null,
//                                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
//                                )
                            }
                        }

                    } else {

                        Box() {

                            Row() {
                                Image(
                                    painter = playerResourceCodeB,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .size(characterSize.dp)
                                    .padding(start = 10.dp)
                            ) {
                                soundViewModel.soundPlay(SoundCode.BATTLE_DEFENCE)
                                DefenceGif()
                            }
                        }
                    }
                } else if (battleViewModel.battleActive.order == "A" && battleViewModel.battleActive.nowTurn % 2 == 0) {
                    if (battleViewModel.battleActive.damageA > 0) {
                        Box() {
                            Row() {
                                Image(
                                    painter = playerResourceCodeB,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row(modifier = Modifier.size(characterSize.dp)) {
                                soundViewModel.soundPlay(SoundCode.BATTLE_ATTACK)
                                AttackGif()
//                                Image(
//                                    painter = attack,
//                                    contentDescription = null,
//                                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
//                                )
                            }
                        }

                    } else {

                        Box() {
                            Row() {
                                Image(
                                    painter = playerResourceCodeB,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .size(characterSize.dp)
                                    .padding(start = 10.dp)
                            ) {
                                soundViewModel.soundPlay(SoundCode.BATTLE_DEFENCE)
                                DefenceGif()
                            }
                        }
                    }

                } else {
                    Box() {
                        Row() {
                            Image(
                                painter = playerResourceCodeB,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(characterSize.dp)
                                    .height(characterSize.dp)
                            )
                        }
                    }
                }

            } else {
                Box() {
                    Row() {
                        Image(
                            painter = playerResourceCodeB,
                            contentDescription = null,
                            modifier = Modifier
                                .width(characterSize.dp)
                                .height(characterSize.dp)
                        )
                    }
                }
            }
            // -----------------------------------------------------------------------------------------------------------------------------------------------


            Row(modifier = Modifier.width(30.dp)) {
                Image(
                    painter = painterResource(R.drawable.battle_me),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(30.dp)
                )

            }
            if (battleViewModel.battleActive.order == "A") {
                if (battleViewModel.nextAttacker == "A") {
                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(attackDefenceSize.dp)
                    )
                } else {
                    Image(
                        painter = defence, contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(attackDefenceSize.dp)
                    )

                }
            } else {
                if (battleViewModel.nextAttacker == "A") {
                    Image(
                        painter = defence, contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(attackDefenceSize.dp)
                    )
                } else {

                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(attackDefenceSize.dp)
                    )
                }
            }

        }
    }
}