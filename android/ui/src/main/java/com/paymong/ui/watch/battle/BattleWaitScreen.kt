package com.paymong.ui.watch.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.paymong.common.code.AnimationCode
import com.paymong.common.navigation.WatchNavItem
import com.paymong.ui.R
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori
import kotlinx.coroutines.CoroutineScope

@Composable
fun BattleWait(
    navController: NavHostController
) {
    val img = painterResource(R.drawable.main_bg)
    Box{
        Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "...배틀을 찾는 중...",
                    textAlign = TextAlign.Center,
                    fontFamily = dalmoori,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 20.dp),
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "기기를 태그 해주세요.", textAlign = TextAlign.Center,
                        fontFamily = dalmoori,)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate(WatchNavItem.BattleFind.route) },
                    modifier = Modifier.height(45.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                ) {
                    Text(text = "다음")
                }
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun BattleWaitPreview() {
    val navController = rememberSwipeDismissableNavController()
    PaymongTheme {
        BattleWait(navController)
    }
}