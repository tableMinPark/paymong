package com.paymong.ui.app.collect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.collect.CollectMapViewModel
import com.paymong.common.R
import com.paymong.common.code.MapCode
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun CollectMap(navController: NavController) {
    val viewModel: CollectMapViewModel = viewModel()
    CollectMapUI(navController, viewModel)
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
fun ComponentRow(index: Int){
    val viewModel : CollectMapViewModel = viewModel()
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
            Component(MapCode.valueOf(viewModel.mapList[index]).mapName, MapCode.valueOf(viewModel.mapList[index]).code)
        }
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .padding(start = 5.dp, end = 15.dp)){
            Component(MapCode.valueOf(viewModel.mapList[index+1]).mapName, MapCode.valueOf(viewModel.mapList[index+1]).code)
        }

    }
}

@Composable
fun CollectMapUI(
    navController: NavController,
    viewModel: CollectMapViewModel
) {
    Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center
) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Map", fontFamily = dalmoori, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                    modifier = Modifier.height(80.dp),
                    actions = {
                        IconButton(onClick = { navController.navigate(AppNavItem.Collect.route + "/${viewModel.memberId}") }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                        }
                    },
                    backgroundColor = PayMongPurple,
                    elevation = 40.dp
                ) },
            backgroundColor = PayMongNavy
        ) {
            Box(Modifier.padding(it)) {
                val scrollState = rememberScrollState()
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    items(16){
                        index -> ComponentRow(index = index*2)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectMapPreview() {
//    val navController = rememberNavController()

    PaymongTheme {
//        CollectMap(navController)
        ComponentRow(0)
//        Component("스타벅스", R.drawable.mp001)
    }
}