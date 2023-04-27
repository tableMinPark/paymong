package com.paymong.ui.watch.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.domain.watch.activity.WalkingViewModel
import com.paymong.domain.watch.main.MainInfoViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun WalkingActive(navController: NavHostController) {
    val viewModel: WalkingViewModel = viewModel()
//    viewModel.setSensor(LocalContext.current)
    WalkingActiveUI(navController, viewModel)
}

@Composable
fun WalkingActiveUI(
    navController: NavHostController,
    viewModel: WalkingViewModel
) {

    val img = painterResource(R.drawable.walking_bg)
    Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.15f).padding(top=10.dp)
        ) {
            Text(text = String.format("%02d:%02d", viewModel.minute, viewModel.second),
                fontFamily = dalmoori,
                fontSize = 20.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top=5.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.5f).padding(start=25.dp)
            ){
                Text(text = String.format("%d", viewModel.count),
                    fontFamily = dalmoori,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Bottom)
                )
                Text(text =" 걸음",
                    fontFamily = dalmoori,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Bottom)
                )}}


        if (viewModel.isWalkingEnd) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(top=25.dp).fillMaxHeight(0.6f)


            ) {

5

                    Text(
                        text = "?산책 종료?",
                        modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(),
                        fontFamily = dalmoori,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color(0xFFffffff)
                    )
            }


        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(top=5.dp)
            ) {
                val viewModel : MainInfoViewModel = viewModel()
                var findCode = viewModel.characterCode
                var chCode = CharacterCode.valueOf(findCode)
                val chA = painterResource(chCode.code)
                Image(painter = chA, contentDescription = null, modifier = Modifier.width(100.dp))

            }
        }

        if (viewModel.isWalkingEnd) {

            Box(modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.4f)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)) {


                Image(
                    painter = painterResource(id = R.drawable.blue_bnt),
                    contentDescription = "blue_bnt",
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().scale(1.2f)
                        .clickable {
                            viewModel.screenClick() {
                                navController.navigate(WatchNavItem.Activity.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                )



                Text(
                text = "YES",
                modifier = Modifier.fillMaxWidth().fillMaxHeight().wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally),
                fontFamily = dalmoori,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color(0xFF0C4DA2)
                )


            }

            Box(modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.4f)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)) {


                Image(
                    painter = painterResource(id = R.drawable.blue_bnt),
                    contentDescription = "blue_bnt",
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().scale(1.2f)
                        .clickable {
                            viewModel.screenClick() {
                                navController.navigate(WatchNavItem.Activity.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                )



                Text(
                    text = "YES",
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontFamily = dalmoori,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color(0xFF0C4DA2)
                )


            }




        }else {

        Box(modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.4f)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)) {


                Image(
                    painter = painterResource(id = R.drawable.blue_bnt),
                    contentDescription = "blue_bnt",
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().scale(1.2f)
                        .clickable {
                            viewModel.screenClick() {
                                navController.navigate(WatchNavItem.Activity.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                )



                    Text(
                        text = "종료",
                        modifier = Modifier.fillMaxWidth().fillMaxHeight().wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        fontFamily = dalmoori,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color(0xFF0C4DA2)
                    )


            }

    } }
}


@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun WalkingPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        WalkingActive(navController)
    }
}