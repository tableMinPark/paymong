package com.paymong.ui.app.collect

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.paymong.common.R
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.CollectMapViewModel
import com.paymong.common.code.MapCode
import com.paymong.domain.SoundViewModel
import com.paymong.ui.app.common.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.dalmoori

@Composable
fun CollectMap(
    navController: NavController,
    collectMapViewModel : CollectMapViewModel = viewModel(),
    soundViewModel: SoundViewModel,
) {
    LaunchedEffect(true) {
        collectMapViewModel.init()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Scaffold(
            topBar = { TopBar("Map", navController, AppNavItem.Collect.route, soundViewModel) },
            backgroundColor = PayMongNavy
        ) {
            Box(Modifier.padding(it)) {
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    var cnt = collectMapViewModel.mapList.size / 2
                    if (collectMapViewModel.mapList.size % 2 == 1) {
                        cnt += 1
                    }
                    if (collectMapViewModel.isLoading.value) {
                        items(cnt) { index ->
                            ComponentRow(index = index * 2, collectMapViewModel)
                        }
                    } else {
                        item {
                            val strokeWidth = 10.dp

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .drawBehind {
                                            drawCircle(
                                                PayMongNavy,
                                                radius = size.width / 2 - strokeWidth.toPx() / 2,
                                                style = Stroke(strokeWidth.toPx())
                                            )
                                        }
                                        .fillMaxWidth(0.3f),
                                    color = Color.LightGray,
                                    strokeWidth = strokeWidth
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Component(mapName :String, mapCode: Int){ // map 이름 + map 사진
    val mapImg = painterResource(mapCode)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = mapName,
            fontFamily = dalmoori,
            color = Color.White,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Box(contentAlignment = Alignment.Center){
            if(mapCode==R.drawable.none){
                Image(
                    painter = mapImg, contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                )
            } else {
                Image(
                    painter = mapImg, contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                        .border(
                            width = 10.dp,
                            Color.White.copy(alpha = 0.5f),
                            RoundedCornerShape(15.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun ComponentRow(
    index: Int,
    collectMapViewModel: CollectMapViewModel
){
    var text = "???"
    var code = R.drawable.none_map
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .padding(start = 15.dp, end = 5.dp)){
            if(collectMapViewModel.mapList[index].name != null){
                text = collectMapViewModel.mapList[index].name.toString()
            }
            if(collectMapViewModel.mapList[index].code != null){
                code = collectMapViewModel.mapList[index].code?.let { MapCode.valueOf(it).code }!!
            }
            Component(text, code)
        }
        text = "???"
        code = R.drawable.none_map
        if(index+1<collectMapViewModel.mapList.size){
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(start = 5.dp, end = 15.dp)){
                if(collectMapViewModel.mapList[index+1].name != null){
                    text = collectMapViewModel.mapList[index+1].name.toString()
                }
                if(collectMapViewModel.mapList[index+1].code != null){
                    code = collectMapViewModel.mapList[index+1].code?.let { MapCode.valueOf(it).code }!!
                }
                Component(text, code)
            }
        } else{
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(start = 5.dp, end = 15.dp)){
                Component("", R.drawable.none)
            }
        }
    }
}