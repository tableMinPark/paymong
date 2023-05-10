package com.paymong.ui.watch.battle

import android.media.SoundPool
import android.os.Build
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MatchingCode
import com.paymong.domain.watch.battle.BattleViewModel
import com.paymong.ui.theme.*
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.domain.watch.activity.TrainingViewModel

@Composable
fun BattleActive(
    navController: NavHostController,
    battleViewModel: BattleViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var characterSize = 0
    var fontSize = 0
    var barWidth = 0
    var barHeight = 0
    var attackDefenceSize = 0
    var attackDefenceSizeSmall = 0

    if (screenWidthDp < 200) {
        characterSize = 80
        barWidth = 60
        barHeight = 15
        fontSize = 20
        attackDefenceSize = 35
        attackDefenceSizeSmall = 25

    }
    else {
        characterSize = 100
        barWidth = 70
        barHeight = 20
        fontSize = 24
        attackDefenceSize = 40
        attackDefenceSizeSmall = 30
    }


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

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("battle", "싸움 애니메이션 ON")

        battleViewModel.matchingState = MatchingCode.SELECT_BEFORE
        }, 4000)
    }



    val bg = painterResource(R.drawable.battle_bg)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop)
    BattleBackgroundGif()
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

            if (battleViewModel.battleActive.order == "A") {
                if (battleViewModel.battleActive.nextAttacker == "A") {
                    Image(
                        painter = defence, contentDescription = null,
                        modifier = Modifier.padding(horizontal = 25.dp).size(attackDefenceSizeSmall.dp)
                    )

                } else {
                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier.padding(horizontal = 25.dp).size(attackDefenceSizeSmall.dp)
                    )
                }
            } else {
                if (battleViewModel.battleActive.nextAttacker == "A") {
                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier.padding(horizontal = 25.dp).size(attackDefenceSizeSmall.dp)
                    )

                } else {
                    Image(
                        painter = defence, contentDescription = null,
                        modifier = Modifier.padding(horizontal = 25.dp).size(attackDefenceSizeSmall.dp)
                    )
                }
            }


            var findCode = ""
            val chCode : CharacterCode
            val player1: Painter

            if (battleViewModel.battleActive.order == "A") {
                findCode = battleViewModel.characterCodeB
                chCode = CharacterCode.valueOf(findCode)
                player1 = painterResource(chCode.resourceCode)

            } else {
                findCode = battleViewModel.characterCodeA
                chCode = CharacterCode.valueOf(findCode)
                player1 = painterResource(chCode.resourceCode)
            }
//                Image(
//                    painter = chA,
//                    contentDescription = null,
//                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
//                )
            if (battleViewModel.matchingState == MatchingCode.ACTIVE_RESULT){
                if (battleViewModel.battleActive.order == "A" && battleViewModel.battleActive.nowTurn % 2 != 0){
                  if ( battleViewModel.battleActive.damageB > 0 ) {

                    Box() {
                        Row() {
                            Image(
                            painter = player1,
                            contentDescription = null,
                            modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                        )}

                        Row(modifier = Modifier.size(characterSize.dp)) {
                            SoundPlay(battleViewModel, "Attack")
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
                            painter = player1,
                            contentDescription = null,
                            modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                        )}

                        Row() {
                            Row(modifier = Modifier.size(characterSize.dp).padding(start=10.dp)) {
                                SoundPlay(battleViewModel, "Defence")
                                DefenceGif()
                            }
                        }
                        }
                    }
                } else if (battleViewModel.battleActive.order == "B" && battleViewModel.battleActive.nowTurn % 2 == 0)  {
                    if ( battleViewModel.battleActive.damageA > 0 ) {
                        Box() {
                            Row() {
                                Image(
                                    painter = player1,
                                    contentDescription = null,
                                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                                )}

                            Row(modifier = Modifier.size(characterSize.dp)) {
                                SoundPlay(battleViewModel, "Attack")
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
                            Row() { Image(
                                painter = player1,
                                contentDescription = null,
                                modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                            )}

                            Row(modifier = Modifier.size(characterSize.dp).padding(start=10.dp)) {
                                SoundPlay(battleViewModel, "Defence")
                                DefenceGif()
                            }
                        }
                    }

                } else {
                    Box() {
                        Row() {
                            Image(
                            painter = player1,
                            contentDescription = null,
                            modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                             )
                        }
                    }
                }

            } else {
                Box() {
                    Row() {
                        Image(
                        painter = player1,
                        contentDescription = null,
                        modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
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
            Box(modifier = Modifier
                .width(barWidth.dp)
                .height(barHeight.dp)
                .clip(CircleShape)
                .background(Color.White)){
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = healA/500)
                    .clip(CircleShape)
                    .background(color = PayMongRed)
                    .padding(8.dp)) {}
            }
            Text(text=String.format("%d/%d", battleViewModel.battleActive.nowTurn, battleViewModel.TOTAL_TURN),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori,
                fontSize = fontSize.sp,
                modifier = Modifier.padding(horizontal = 10.dp))
            //게이지 바
            var damB = battleViewModel.battleActive.damageB.toFloat()
            var healB = battleViewModel.battleActive.healthB.toFloat()
            Box(modifier = Modifier
                .width(barWidth.dp)
                .height(barHeight.dp)
                .clip(CircleShape)
                .background(Color.White)){
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = healB/500)
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
            // player2 :: 아래쪽
            var findCode = ""
            val chCode : CharacterCode
            val player2: Painter

            if (battleViewModel.battleActive.order == "A") {
                findCode = battleViewModel.characterCodeA
                chCode = CharacterCode.valueOf(findCode)
                player2 = painterResource(chCode.resourceCode)

            } else {
                findCode = battleViewModel.characterCodeB
                chCode = CharacterCode.valueOf(findCode)
                player2 = painterResource(chCode.resourceCode)
            }

//            Image(painter = player2, contentDescription = null, modifier = Modifier.width(characterSize.dp).height(characterSize.dp))

            // ------------------------------------------------------------------------------------------------------------------------------------------------


            if (battleViewModel.matchingState == MatchingCode.ACTIVE_RESULT){
                if (battleViewModel.battleActive.order == "B" && battleViewModel.battleActive.nowTurn % 2 != 0){
                    if ( battleViewModel.battleActive.damageB > 0 ) {

                        Box() {
                            Row() {
                                Image(
                                    painter = player2,
                                    contentDescription = null,
                                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                                )}

                            Row(modifier = Modifier.size(characterSize.dp)) {
                                SoundPlay(battleViewModel, "Attack")
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
                                    painter = player2,
                                    contentDescription = null,
                                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                                )}

                            Row(modifier = Modifier.size(characterSize.dp).padding(start=10.dp)) {
                                SoundPlay(battleViewModel, "Defence")
                                DefenceGif()
                            }
                        }
                    }
                } else if (battleViewModel.battleActive.order == "A" && battleViewModel.battleActive.nowTurn % 2 == 0)  {
                    if ( battleViewModel.battleActive.damageA > 0 ) {
                        Box() {
                            Row() {
                                Image(
                                    painter = player2,
                                    contentDescription = null,
                                    modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                                )}

                            Row(modifier = Modifier.size(characterSize.dp)) {
                                SoundPlay(battleViewModel, "Attack")
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
                            Row() { Image(
                                painter = player2,
                                contentDescription = null,
                                modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                            )}

                            Row(modifier = Modifier.size(characterSize.dp).padding(start=10.dp)) {
                                SoundPlay(battleViewModel, "Defence")
                                DefenceGif()
                            }
                        }
                    }

                } else {
                    Box() {
                        Row() {
                            Image(
                                painter = player2,
                                contentDescription = null,
                                modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                            )
                        }
                    }
                }

            } else {
                Box() {
                    Row() {
                        Image(
                            painter = player2,
                            contentDescription = null,
                            modifier = Modifier.width(characterSize.dp).height(characterSize.dp)
                        )
                    }
                }
            }













            // -----------------------------------------------------------------------------------------------------------------------------------------------


            Row(modifier=Modifier.width(30.dp)) {
                Image( painter = painterResource(R.drawable.battle_me) ,  contentDescription = null,modifier = Modifier.padding(top=50.dp).size(30.dp))

            }
            if (battleViewModel.battleActive.order == "A") {
                if (battleViewModel.battleActive.nextAttacker == "A") {
                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier.padding(start=10.dp).size(attackDefenceSize.dp)
                    )
                } else {
                    Image(
                    painter = defence, contentDescription = null,
                    modifier = Modifier.padding(start=10.dp).size(attackDefenceSize.dp)
                )

                }
            } else {
                if (battleViewModel.battleActive.nextAttacker == "A") {
                    Image(
                        painter = defence, contentDescription = null,
                        modifier = Modifier.padding(start=10.dp).size(attackDefenceSize.dp)
                    )
                } else {

                    Image(
                        painter = attack, contentDescription = null,
                        modifier = Modifier.padding(start=10.dp).size(attackDefenceSize.dp)
                    )
                }
            }

        }}
    }


//    var cnt by remember { mutableStateOf(viewModel.count) }


@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleActivePreview() {
    val navController = rememberSwipeDismissableNavController()
    val viewModel : BattleViewModel = viewModel()

    PaymongTheme {
        BattleActive(navController, viewModel)
    }
}




@ExperimentalCoilApi
@Composable
fun AttackGif(

) {
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
            data = R.drawable.attack_motion,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}

@ExperimentalCoilApi
@Composable
fun DefenceGif(

) {
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
            data = R.drawable.defence_motion,
            builder = {
                size(OriginalSize)
            }
        ),
        contentDescription = null,
        modifier = Modifier
//            .padding(top = 10.dp)
    )
}

fun SoundPlay (battleViewModel: BattleViewModel, soundName : String) {
    if (soundName == "Bnt") {
        battleViewModel.soundPool.play(battleViewModel.buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
    else if (soundName == "Win") {
        battleViewModel.soundPool.play(battleViewModel.winSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
    else if (soundName == "Lose") {
        battleViewModel.soundPool.play(battleViewModel.loseSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
    else if (soundName == "Attack") {
        battleViewModel.soundPool.play(battleViewModel.attackSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
    else if (soundName == "Defence") {
        battleViewModel.soundPool.play(battleViewModel.defenceSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }


}