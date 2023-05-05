package com.paymong.ui.watch.main

import android.media.SoundPool
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.main.MainInteractionViewModel
import com.paymong.common.R
import com.paymong.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainInteraction(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    val viewModel: MainInteractionViewModel = viewModel()
    MainInteractionUI(animationState, pagerState, coroutineScope, navController, viewModel)

}





@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainInteractionUI(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    viewModel: MainInteractionViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    var buttonSize = 0
    var buttonIconSize = 0
    var boxHeight = 0
    var boxWidth = 0
    var marginTop = 0
    var thirdRowPadding = 0

    if (screenWidthDp < 200) {
        buttonSize = 45
        buttonIconSize = 25
        boxHeight = 50
        boxWidth = 60
        marginTop = 40
        thirdRowPadding = 12
    }
    else {
        buttonSize = 57
        buttonIconSize = 35
        boxHeight = 60
        boxWidth = 80
        marginTop = 50
        thirdRowPadding = 15
    }



    val soundPool = SoundPool.Builder()
        .setMaxStreams(1) // 동시에 재생 가능한 스트림의 최대 수
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)


    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }

    Column(
        modifier = Modifier.padding(15.dp)//, bottom = 15.dp)
                .fillMaxSize(1f)
    ) {


        Box () {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
//
                Box(
                    modifier = Modifier.clickable {
                        ButtonSoundPlay()
                        navController.navigate(WatchNavItem.Battle.route)
                    }
                        .width(boxWidth.dp).height(boxHeight.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(buttonSize.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        val battle = painterResource(R.drawable.battle)
                        val interactionBnt = painterResource(R.drawable.interaction_bnt)
                        val interactionBntBorder = painterResource(R.drawable.interaction_bnt_pink)
                        Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f,)
                        Image(painter = interactionBntBorder, contentDescription = null,)
                        Image(painter = battle, contentDescription = null,  modifier = Modifier.size(buttonIconSize.dp))
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(top=marginTop.dp, bottom= 5.dp)
            ) {

                Box(
                    modifier = Modifier.clickable {
                        ButtonSoundPlay()
                        navController.navigate(WatchNavItem.Feed.route)
                    }
                        .width(boxWidth.dp).height(boxHeight.dp).padding(start = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(buttonSize.dp),
                        contentAlignment = Alignment.Center
                    )

                    {
//                Text(text = "FEED", textAlign = TextAlign.Center)
                        val feed = painterResource(R.drawable.feed)
                        val interactionBnt = painterResource(R.drawable.interaction_bnt)
                        val interactionBntBorder = painterResource(R.drawable.interaction_bnt_orange)
                        Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f,)
                        Image(painter = interactionBntBorder, contentDescription = null,)
                        Image(
                            painter = feed,
                            contentDescription = null,
                            modifier = Modifier.size(buttonIconSize.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier.clickable {
                        ButtonSoundPlay()
                        navController.navigate(WatchNavItem.Activity.route)
                    }
                        .width(boxWidth.dp).height(boxHeight.dp).padding(end = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier.size(buttonSize.dp),
                        contentAlignment = Alignment.Center
                    )

                    {
//                Text(text = "Activity", textAlign = TextAlign.Center)
                        val activity = painterResource(R.drawable.activity)
                        val interactionBnt = painterResource(R.drawable.interaction_bnt)
                        val interactionBntBorder = painterResource(R.drawable.interaction_bnt_green)
                        Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f,)
                        Image(painter = interactionBntBorder, contentDescription = null,)
                        Image(
                            painter = activity,
                            contentDescription = null,
                            modifier = Modifier.size(buttonIconSize.dp)
                        )
                    }
                }
            }
        }



        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
        ) {

            Box(
                modifier = Modifier.clickable {
                    ButtonSoundPlay()
                    animationState.value = AnimationCode.Sleep
                    navController.navigate(WatchNavItem.Main.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        coroutineScope.launch { pagerState.animateScrollToPage(1) }
                    }
                }.width(boxWidth.dp) .height(boxHeight.dp).padding(start = thirdRowPadding.dp),
                contentAlignment = Alignment.Center
            )

            {   Box ( modifier = Modifier.size(buttonSize.dp),
                contentAlignment = Alignment.Center
            )
            {
//                Text(text = "SLEEP", textAlign = TextAlign.Center)
                val sleep = painterResource(R.drawable.sleep)
                val interactionBnt = painterResource(R.drawable.interaction_bnt)
                val interactionBntBorder = painterResource(R.drawable.interaction_bnt_blue)
                Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f,)
                Image(painter = interactionBntBorder, contentDescription = null,)
                Image(
                    painter = sleep,
                    contentDescription = null,
                    modifier = Modifier.size(buttonIconSize.dp)
                )
            }
        }

            Box ( modifier = Modifier.clickable {
                ButtonSoundPlay()
                animationState.value = AnimationCode.Poop
                navController.navigate(WatchNavItem.Main.route){
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop =true
                    coroutineScope.launch {pagerState.animateScrollToPage(1) }
                }}.width(boxWidth.dp) .height(boxHeight.dp).padding(end = thirdRowPadding.dp),
                contentAlignment = Alignment.Center
            )
            {    Box ( modifier = Modifier.size(buttonSize.dp),
                contentAlignment = Alignment.Center
            )
                {
//                Text(text = "POOP", textAlign = TextAlign.Center)
                    val poop = painterResource(R.drawable.poop)
                    val interactionBnt = painterResource(R.drawable.interaction_bnt)
                    val interactionBntBorder = painterResource(R.drawable.interaction_bnt_purple)
                    Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f,)
                    Image(painter = interactionBntBorder, contentDescription = null,)
                    Image(
                        painter = poop,
                        contentDescription = null,
                        modifier = Modifier.size(buttonIconSize.dp)
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalPagerApi::class)
@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainInteractionPreview() {
    val animationState = remember { mutableStateOf(AnimationCode.Normal) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        MainInteraction(animationState, pagerState, coroutineScope, navController)
    }
}