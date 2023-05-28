package com.paymong.ui.app.things

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.paymong.common.R
import com.paymong.common.code.SoundCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.SmartThingsViewModel
import com.paymong.ui.app.common.TopBar
import com.paymong.ui.theme.*

@Composable
fun AddSmartThings(
    navController: NavController,
    soundViewModel: SoundViewModel,
    smartThingsViewModel: SmartThingsViewModel
) {
    LaunchedEffect(true) {
        smartThingsViewModel.reset()
    }

    Scaffold(
        topBar = { TopBar("스마트싱스", navController, AppNavItem.SmartThings.route, soundViewModel) },
        backgroundColor = PayMongNavy
    ) { paddingValue ->
        Box(Modifier.padding(paddingValue)) {
            smartThingsViewModel.toConnectThings()

            val dialogOpen = remember { mutableStateOf(false) }
            val msg = remember { mutableStateOf("") }
            if(dialogOpen.value){
                Alert(
                    setShowDialog = { dialogOpen.value = it },
                    msg.value
                )
            }
            if(smartThingsViewModel.isAdd){
                smartThingsViewModel.isAdd = false
                navController.navigate(AppNavItem.SmartThings.route) {
                    popUpTo("smart_things") {
                        inclusive = true
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Desc()
                Spacer(modifier = Modifier.height(20.dp))
                AddThings(smartThingsViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                SelectThingsList(smartThingsViewModel)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painterResource(R.drawable.btn_2_bg), contentDescription = null,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                                    if (smartThingsViewModel.routine == "") {
                                        msg.value = "루틴 이름을 입력해주세요."
                                        dialogOpen.value = true
                                    } else if (smartThingsViewModel.isSelect == -1) {
                                        msg.value = "연동할 기기를 선택해주세요."
                                        dialogOpen.value = true
                                    }

                                    if (smartThingsViewModel.routine != "" && smartThingsViewModel.isSelect != -1) {
                                        smartThingsViewModel.addThings()
                                        smartThingsViewModel.isAdd = true
                                    }
                                }
                            )
                            .width(110.dp)
                            .height(80.dp)
                    )
                    Text(
                        text = "추가", textAlign = TextAlign.Center,
                        fontFamily = dalmoori, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White
                    )
                }
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
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Text("설명서",textAlign = TextAlign.Center, fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text("1. 스마트싱스 어플 접속 후 자동화 페이지 접속", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("2. + 버튼 클릭 후 루틴 만들기 클릭", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("3. 루틴을 시작할 조건 선택 후 기기상태 선택", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("4. 설정할 기기와 이벤트 선택 후 완료", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("5. 루틴 동작 설정 클릭", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("6. 누군가에게 알려주기 클릭", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("7. 멤버에게 알림보내기", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("8. 알림메시지 입력 후 완료", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("9. 루틴이름 설정하기", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
                Text("10. 설정 완료 후 아래에 정보 입력", fontFamily = dalmoori, fontSize = 13.sp, color = Color.White, modifier = Modifier.padding(bottom = 10.dp))
            }
        }
    }
}

@Composable
private fun AddThings(smartThingsViewModel:SmartThingsViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        TextField(
            value = smartThingsViewModel.routine,
            onValueChange = { newText -> smartThingsViewModel.routine = newText },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            textStyle = TextStyle(fontFamily = dalmoori, fontSize = 15.sp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = PayMongRed200,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                placeholderColor = PayMongRed200
            ),
            placeholder = { Text("루틴이름을 입력해주세요.", fontFamily = dalmoori, fontSize = 15.sp) }
        )
    }
}

@Composable
private fun SelectThingsList(smartThingsViewModel:SmartThingsViewModel){
    var isOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
        Box(modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
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
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "연동할 기기",
                        textAlign = TextAlign.Center,
                        fontFamily = dalmoori,
                        fontSize = 15.sp,
                        color = Color.White,
                        modifier = Modifier.weight(0.4f)
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painterResource(R.drawable.btn_2_bg), contentDescription = null,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        isOpen = !isOpen
                                        if (smartThingsViewModel.thingsList.size == 0) {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "연동할 기기 목록이 없습니다.",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    }
                                )
                                .width(110.dp)
                                .height(50.dp)
                        )
                        Text(
                            text = "선택", textAlign = TextAlign.Center,
                            fontFamily = dalmoori, fontSize = 15.sp, color = Color.White
                        )
                    }
                }
            }
            if(isOpen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    for(i in 0 until smartThingsViewModel.thingsList.size) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 15.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        smartThingsViewModel.isSelect = i
                                    }
                                )
                        ) {
                            Text(
                                smartThingsViewModel.thingsList[i].thingsName,
                                textAlign = TextAlign.Start,
                                fontFamily = dalmoori,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                            if(i == smartThingsViewModel.isSelect){
                                Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier =  Modifier.size(15.dp))
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .height(1.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Alert(
    setShowDialog: (Boolean) -> Unit,
    msg: String
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
                Text(msg,
                    fontFamily = dalmoori,
                    color = Color.Black)
                Button(
                    onClick = {
                        setShowDialog(false)
                    },
                    colors = ButtonDefaults.buttonColors(PayMongBlue),
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                ) {
                    Text(text = "확인", fontFamily = dalmoori, color = Color.Black)
                }
            }
        }
    }
}