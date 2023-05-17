package com.paymong.ui.watch.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.common.R
import com.paymong.common.code.MongStateCode
import com.paymong.common.code.SoundCode
import com.paymong.common.code.ToastMessage
import com.paymong.domain.watch.WatchViewModel
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.watch.common.showToast
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
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val buttonSize = if(screenWidthDp < 200) 45 else 50
    val buttonIconSize = if(screenWidthDp < 200) 25 else 30
    val boxHeight = if(screenWidthDp < 200) 50 else 60
    val boxWidth = if(screenWidthDp < 200) 60 else 80
    val marginTop = if(screenWidthDp < 200) 40 else 50
    val thirdRowPadding = if(screenWidthDp < 200) 12 else 15
    val eggState = watchViewModel.mong.mongCode.code
    var isBtnActive = true
    var sleepActive = true

    if(watchViewModel.isNavToMainClick){
        watchViewModel.isNavToMainClick = false
        navController.navigate(WatchNavItem.Main.route) {
            coroutineScope.launch { pagerState.scrollToPage(1) }
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    val interactionBnt = painterResource(R.drawable.interaction_bnt)

    if (eggState.slice(0..3) == "CH00") {
        isBtnActive = false
        sleepActive = false
    }
    else if(watchViewModel.stateCode == MongStateCode.CD002){
        isBtnActive = false
        sleepActive = true
    }
    else if(watchViewModel.stateCode == MongStateCode.CD005){
        isBtnActive = false
        sleepActive = false
    }


    Column(
        modifier = Modifier
            .padding(15.dp)//, bottom = 15.dp)
            .fillMaxSize(1f)
    ) {
        Box  {
            //BATTLE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .clickable {
                            if (isBtnActive) {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            navController.navigate(WatchNavItem.Battle.route)
                            }
                            else {
                                showToast(context, ToastMessage.BUTTON_NOT_ACTIVE)
                            }
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
                        val interactionBntBorder = painterResource(R.drawable.interaction_bnt_pink)
                        Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f)
                        Image(painter = interactionBntBorder, contentDescription = null)
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
                            if (isBtnActive) {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            navController.navigate(WatchNavItem.Feed.route)
                            }
                            else {
                                showToast(context, ToastMessage.BUTTON_NOT_ACTIVE)
                            }
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
                        val interactionBntBorder = painterResource(R.drawable.interaction_bnt_orange)
                        Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f)
                        Image(painter = interactionBntBorder, contentDescription = null)
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
                            if (isBtnActive) {
                                soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                                navController.navigate(WatchNavItem.Activity.route)
                            }
                            else {
                                showToast(context, ToastMessage.BUTTON_NOT_ACTIVE)
                            }
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
                        val interactionBntBorder = painterResource(R.drawable.interaction_bnt_green)
                        Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f)
                        Image(painter = interactionBntBorder, contentDescription = null)
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
                        if (isBtnActive || sleepActive) {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            watchViewModel.isNavToMainClick = true
                            animationState.value = AnimationCode.Sleep
                            watchViewModel.sleep()
                        }
                        else {
                            showToast(context, ToastMessage.BUTTON_NOT_ACTIVE)
                        }
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
                    val interactionBntBorder = painterResource(R.drawable.interaction_bnt_blue)
                    Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f)
                    Image(painter = interactionBntBorder, contentDescription = null)
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
                        if (isBtnActive) {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            animationState.value = AnimationCode.Poop
                            watchViewModel.poop()
                            watchViewModel.isNavToMainClick = true
                        }
                        else {
                            showToast(context, ToastMessage.BUTTON_NOT_ACTIVE)
                        }
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
                    val interactionBntBorder = painterResource(R.drawable.interaction_bnt_purple)
                    Image(painter = interactionBnt, contentDescription = null, alpha = 0.8f)
                    Image(painter = interactionBntBorder, contentDescription = null)
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