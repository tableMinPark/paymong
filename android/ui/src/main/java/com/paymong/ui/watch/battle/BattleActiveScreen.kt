package com.paymong.ui.watch.battle

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
import com.paymong.domain.SoundViewModel
import com.paymong.domain.watch.WatchViewModel
import com.paymong.ui.watch.common.AttackGif
import com.paymong.ui.watch.common.Background
import com.paymong.ui.watch.common.BattleBackgroundGif
import com.paymong.ui.watch.common.DefenceGif

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BattleActive(
    navController: NavHostController,
    soundViewModel: SoundViewModel,
    battleViewModel: BattleViewModel
) {
    Background(false)
    BattleBackgroundGif()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val characterSize = if (screenWidthDp < 200) 80 else 100
    val fontSize = if (screenWidthDp < 200) 20 else 25
    val barWidth = if (screenWidthDp < 200) 60 else 70
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

            Handler(Looper.getMainLooper()).postDelayed({
                navController.navigate(WatchNavItem.BattleEnd.route) {
                    popUpTo(0)
                    launchSingleTop = true
                }
            }, 3000)
            battleViewModel.battleEnd()
        }
        MatchingCode.ACTIVE_RESULT -> {
            Handler(Looper.getMainLooper()).postDelayed({
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
                        painter = defence, contentDescription = null, modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .size(attackDefenceSizeSmall.dp)
                    )
                }
            }
            if (battleViewModel.matchingState == MatchingCode.ACTIVE_RESULT) {
                if ((battleViewModel.battleActive.order == "A" && battleViewModel.battleActive.nowTurn % 2 != 0) ) {
                    if (battleViewModel.battleActive.damageB > 0) {
                        Box {
                            Row {
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
                            }
                        }

                    } else {
                        Box {
                            Row {
                                Image(
                                    painter = playerResourceCodeA,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(characterSize.dp)
                                        .height(characterSize.dp)
                                )
                            }
                            Row {
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
                } else if ((battleViewModel.battleActive.order == "B" && battleViewModel.battleActive.nowTurn % 2 == 0) || (battleViewModel.battleActive.order == "B" && battleViewModel.battleActive.nowTurn == -1)) {
                    if (battleViewModel.battleActive.damageA > 0) {
                        Box{
                            Row {
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
                            }
                        }

                    } else {

                        Box {
                            Row {
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
                    Box {
                        Row {
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
                Box {
                    Row {
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
            var leftHeal = battleViewModel.battleActive.healthA.toFloat()
            if (battleViewModel.battleActive.order == "B") {
                leftHeal = battleViewModel.battleActive.healthB.toFloat()
            }
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
                        .fillMaxWidth(fraction = leftHeal / 500)
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
            var rightHeal = battleViewModel.battleActive.healthB.toFloat()
            if (battleViewModel.battleActive.order == "B") {
                rightHeal = battleViewModel.battleActive.healthA.toFloat()
            }


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
                        .fillMaxWidth(fraction = rightHeal / 500)
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
            if (battleViewModel.matchingState == MatchingCode.ACTIVE_RESULT) {
                if ( (battleViewModel.battleActive.order == "B" && battleViewModel.battleActive.nowTurn % 2 != 0)  ) {
                    if (battleViewModel.battleActive.damageB > 0) {

                        Box {
                            Row {
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
                            }
                        }

                    } else {

                        Box {

                            Row {
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
                } else if ((battleViewModel.battleActive.order == "A" && battleViewModel.battleActive.nowTurn % 2 == 0) || (battleViewModel.battleActive.order == "A" && battleViewModel.battleActive.nowTurn == -1)) {
                    if (battleViewModel.battleActive.damageA > 0) {
                        Box {
                            Row {
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
                            }
                        }

                    } else {

                        Box {
                            Row {
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
                    Box {
                        Row {
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
                Box {
                    Row {
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