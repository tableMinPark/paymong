package com.paymong.ui.app.collect

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.collect.CollectPayMongViewModel
import com.paymong.ui.app.component.TopBar
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
fun PayMongHeader(title:String, open: Boolean, setIsOpen:(Boolean)->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(color = Color.White.copy(alpha = 0.5f)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var icon = 0
        Text(title, fontFamily = dalmoori, fontSize = 20.sp, color = Color.White, modifier = Modifier.padding(start = 20.dp))
        icon = if(open){
            R.drawable.close
        } else{ //status == close
            R.drawable.open
        }
        Image(painterResource(icon), contentDescription = null
            , modifier = Modifier
                .padding(end = 20.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { setIsOpen(!open) }
                ))
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
                val isOpen = remember { mutableStateOf(true) }
                val grouped = viewModel.mongList.groupBy { it.substring(2,3) }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    grouped.forEach { (level, mongforLevel) ->
                        stickyHeader {
                            var title = ""
                            title =
                                if(level == "0") "알" else if(level == "1") "1단계" else if(level == "2") "2단계" else "3단계"
                            PayMongHeader(title, isOpen.value, setIsOpen = { isOpen.value = it })
                        }


                        items(mongforLevel.size) { mong ->
                            val mongImg = CharacterCode.valueOf(mongforLevel[mong]).code
                            Row() {
                                Image(painterResource(mongImg), contentDescription = null, modifier = Modifier.fillMaxWidth(0.3f))
                            }
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