package com.paymong.ui.app.collect

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.CollectMapViewModel
import com.paymong.common.code.MapCode
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun CollectMap(
    navController: NavController,
    collectMapViewModel : CollectMapViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Scaffold(
            topBar = {TopBar("Map", navController, AppNavItem.Collect.route)},
            backgroundColor = PayMongNavy
        ) {
            Box(Modifier.padding(it)) {
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    items(16){
                            index -> ComponentRow(index = index*2, collectMapViewModel)
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
            Image(
                painter = mapImg, contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .border(width = 10.dp,Color.White.copy(alpha = 0.5f),RoundedCornerShape(15.dp))
            )
        }
    }
}

@Composable
fun ComponentRow(
    index: Int,
    collectMapViewModel: CollectMapViewModel
){
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
            Component(MapCode.valueOf(collectMapViewModel.mapList[index]).mapName, MapCode.valueOf(collectMapViewModel.mapList[index]).code)
        }
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .padding(start = 5.dp, end = 15.dp)){
            Component(MapCode.valueOf(collectMapViewModel.mapList[index+1]).mapName, MapCode.valueOf(collectMapViewModel.mapList[index+1]).code)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CollectMapPreview() {
//    val navController = rememberNavController()

    PaymongTheme {
//        CollectMap(navController)
//        ComponentRow(0)
//        Component("스타벅스", R.drawable.mp001)
    }
}