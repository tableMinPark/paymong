package com.paymong.ui.app.things

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.SmartThingsViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.common.R
import com.paymong.common.code.SoundCode
import com.paymong.domain.SoundViewModel
import com.paymong.ui.theme.PayMongBlue
import com.paymong.ui.theme.PaymongTheme
import com.paymong.ui.theme.dalmoori

@Composable
fun SmartThings(
    navController: NavController,
    soundViewModel: SoundViewModel,
    smartThingsViewModel: SmartThingsViewModel = viewModel(),

) {
    Scaffold(
        topBar = { TopBar("스마트싱스", navController, AppNavItem.Main.route, soundViewModel) },
        backgroundColor = PayMongNavy
    ) {
        if(smartThingsViewModel.isDelete){
            smartThingsViewModel.isDelete = false
            navController.navigate(AppNavItem.SmartThings.route) {
                popUpTo("smart_things") {
                    inclusive = true
                }
            }
        }
        Box(Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Desc()
                Spacer(modifier = Modifier.height(20.dp))
                AddThings(navController, soundViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                ThingsList(smartThingsViewModel)
            }
        }
    }
}

@Composable
private fun Desc(){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(R.drawable.things), contentDescription = null, modifier = Modifier.size(100.dp))
                Spacer(modifier = Modifier.width(50.dp))
                Text(text = "스마트싱스로\n페이몽 관리", fontFamily = dalmoori, fontSize = 20.sp, color = Color.White, lineHeight = 40.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("청소기 등록,\n청소 하기", textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(painterResource(R.drawable.vacuum), contentDescription = null, modifier = Modifier.size(80.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Image(painterResource(R.drawable.rightbnt) , contentDescription = null, modifier = Modifier.size(30.dp))
                Spacer(modifier = Modifier.width(20.dp))
                Column() {
                    Text("페이몽도\n자동 청소", textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(painterResource(R.drawable.ch100), contentDescription = null, modifier = Modifier.size(80.dp))
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text("실생활과 연동된 페이몽을 즐겨보세요", fontFamily = dalmoori, fontSize = 15.sp, color = Color.White)
        }
    }
}

@Composable
private fun AddThings(navController:NavController, soundViewModel: SoundViewModel){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("나만의 스마트 싱스", fontFamily = dalmoori, fontSize = 15.sp, color = Color.White)
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Image(painterResource(R.drawable.btn_2_bg), contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                                navController.navigate(AppNavItem.AddSmartThings.route) }
                        )
                        .width(110.dp)
                        .height(50.dp)
                )
                Text(
                    text = "추가하기", textAlign = TextAlign.Center,
                    fontFamily = dalmoori, fontSize = 15.sp, color = Color.White
                )
            }
        }
    }
}

@Composable
private fun ThingsList(smartThingsViewModel:SmartThingsViewModel){
    val dialogOpen = remember { mutableStateOf(false) }
    if(dialogOpen.value){
        DeleteThings(
            setShowDialog = { dialogOpen.value = it },
            smartThingsViewModel
        )
    }

    Box(modifier = Modifier
        .fillMaxHeight()
        .clip(RoundedCornerShape(30.dp))
        .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.TopCenter
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = Color.White.copy(alpha = 0.2f))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("연동된 기기명", textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 15.sp, color = Color.White, modifier = Modifier.weight(0.4f))
                    Text("루틴 이름", textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 15.sp, color = Color.White, modifier = Modifier.weight(0.4f))
                    Text("", fontFamily = dalmoori, fontSize = 15.sp, color = Color.White, modifier = Modifier.weight(0.2f))
                }
            }
            LazyColumn(){
                items(smartThingsViewModel.connectedThingsList.size){index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(smartThingsViewModel.connectedThingsList[index].thingsName, textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 15.sp, color = Color.White, modifier = Modifier.weight(0.4f))
                        Text(smartThingsViewModel.connectedThingsList[index].routine, textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 15.sp, color = Color.White, modifier = Modifier.weight(0.4f))
                        Icon(Icons.Filled.Close, contentDescription = null, tint = Color.White, modifier = Modifier
                            .weight(0.2f)
                            .clickable {
                                dialogOpen.value = true
                                smartThingsViewModel.thingsId = smartThingsViewModel.connectedThingsList[index].thingsId
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteThings(
    setShowDialog: (Boolean) -> Unit,
    smartThingsViewModel: SmartThingsViewModel
){
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp),
            color = Color.White.copy(alpha = 0.8f)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("삭제하시겠습니까?",
                    fontFamily = dalmoori,
                    color = Color.Black)
                Row(){
                    Button(
                        onClick = {
                            setShowDialog(false)
                            smartThingsViewModel.isDelete = true
                            smartThingsViewModel.deleteThings()
                        },
                        colors = ButtonDefaults.buttonColors(PayMongBlue),
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                    ) {
                        Text(text = "네", fontFamily = dalmoori, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(PayMongBlue),
                        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                    ) {
                        Text(text = "아니요", fontFamily = dalmoori, color = Color.Black)
                    }
                }

            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ThingsPreview() {
    val smartThingsViewModel:SmartThingsViewModel = viewModel()
    PaymongTheme {
        ThingsList(smartThingsViewModel)
    }
}