package com.paymong.ui.app.collect

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.volley.Header
import com.paymong.common.R
import com.paymong.common.code.AnimationCode
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MapCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.collect.CollectPayMongViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongGreen
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.PayMongPurple
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun CollectPayMong(navController: NavController) {
    val viewModel: CollectPayMongViewModel = viewModel()
    CollectPayMongUI(navController, viewModel)
}

@Composable
fun PayMongHeader(title:String){
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(color = PayMongPurple),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(title, fontFamily = dalmoori, fontSize = 20.sp, color = Color.White, modifier = Modifier.padding(start = 20.dp))
        }
    }
}
@Composable
fun ImageList(list:List<String>, index:Int){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        for(i in 1..3){
            var mongImg = R.drawable.none
            var text = ""
            if((index + i - 1)<list.size) {
                mongImg = CharacterCode.valueOf(list[index + i - 1]).code
                text = CharacterCode.valueOf(list[index + i - 1]).codeName
            }
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(10.dp)){
                Column(
                    modifier = Modifier.fillMaxHeight(),

                ) {
                    val name = text.split(" ")
                    Text(name[0], fontFamily = dalmoori, fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(2.dp))
                    if(name.size>1) {
                        Text(name[1], fontFamily = dalmoori, fontSize = 16.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    if(mongImg == R.drawable.none){
                            Image(painterResource(mongImg), contentDescription = null)
                    } else{
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = Color.White.copy(alpha = 0.5f))
                        ){
                            Image(painterResource(mongImg), contentDescription = null, modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(15.dp)))
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectPayMongUI(
    navController: NavController,
    viewModel: CollectPayMongViewModel
) {
    val isOpen by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Scaffold(
            topBar = {TopBar("PayMong", navController, AppNavItem.Collect.route+ "/${viewModel.memberId}")},
            backgroundColor = PayMongNavy
        ) {
            Box(Modifier.padding(it)) {
                val grouped = viewModel.mongList.groupBy { it.substring(2,3) }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    grouped.forEach { (level, mongforLevel) ->
                        stickyHeader {
                            var title = ""
                            title =
                                if(level == "0") "알" else if(level == "1") "1단계" else if(level == "2") "2단계" else "3단계"
                            PayMongHeader(title)
                        }

                        var cnt = 0
                        cnt = if(mongforLevel.size%3==0){
                            mongforLevel.size/3
                        } else{
                            mongforLevel.size/3+1
                        }
                        items(cnt) { mong ->
                            ImageList(mongforLevel, mong*3)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectPayMongPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        CollectPayMong(navController)
    }
}