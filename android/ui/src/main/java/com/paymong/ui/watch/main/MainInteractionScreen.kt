package com.paymong.ui.watch.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.SoundCode
import com.paymong.domain.watch.WatchViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainInteraction(
    animationState: MutableState<AnimationCode>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    watchViewModel : WatchViewModel,
    soundViewModel : SoundViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val buttonSize = if(screenWidthDp < 200) 45 else 57
    val buttonIconSize = if(screenWidthDp < 200) 25 else 35
    val boxHeight = if(screenWidthDp < 200) 50 else 60
    val boxWidth = if(screenWidthDp < 200) 60 else 80
    val marginTop = if(screenWidthDp < 200) 40 else 50
    val thirdRowPadding = if(screenWidthDp < 200) 12 else 15

    if(watchViewModel.isClicked){
        watchViewModel.isClicked = false
        navController.navigate(WatchNavItem.Main.route) {
            coroutineScope.launch { pagerState.scrollToPage(1) }
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .padding(15.dp)//, bottom = 15.dp)
            .fillMaxSize(1f)
    ) {
        Box () {
            //BATTLE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clickable {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            navController.navigate(WatchNavItem.Battle.route)
                        }
                        .width(boxWidth.dp)
                        .height(boxHeight.dp),
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

            //FEED ACTIVITY
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = marginTop.dp, bottom = 5.dp)
            ) {
                //FEED
                Box(
                    modifier = Modifier
                        .clickable {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            navController.navigate(WatchNavItem.Feed.route)
                        }
                        .width(boxWidth.dp)
                        .height(boxHeight.dp)
                        .padding(start = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(buttonSize.dp),
                        contentAlignment = Alignment.Center
                    ){
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

                //ACTIVITY
                Box(
                    modifier = Modifier
                        .clickable {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            navController.navigate(WatchNavItem.Activity.route)
                        }
                        .width(boxWidth.dp)
                        .height(boxHeight.dp)
                        .padding(end = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier.size(buttonSize.dp),
                        contentAlignment = Alignment.Center
                    ){
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

        //SLEEP POOP
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            //SLEEP
            Box(
                modifier = Modifier
                    .clickable {
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        watchViewModel.isClicked = true
                        animationState.value = AnimationCode.Sleep
                        watchViewModel.sleep()
                    }
                    .width(boxWidth.dp)
                    .height(boxHeight.dp)
                    .padding(start = thirdRowPadding.dp),
                contentAlignment = Alignment.Center
            ){
                Box (
                    modifier = Modifier.size(buttonSize.dp),
                    contentAlignment = Alignment.Center
                ) {
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

            //POOP
            Box (
                modifier = Modifier
                    .clickable {
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        animationState.value = AnimationCode.Poop
                        watchViewModel.poop()
                        watchViewModel.isClicked = true
                    }
                    .width(boxWidth.dp)
                    .height(boxHeight.dp)
                    .padding(end = thirdRowPadding.dp),
                contentAlignment = Alignment.Center
            ){
                Box (
                    modifier = Modifier.size(buttonSize.dp),
                    contentAlignment = Alignment.Center
                ){
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