package com.paymong.ui.app.things

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.SmartThingsViewModel
import com.paymong.ui.app.component.TopBar
import com.paymong.ui.theme.*

@Composable
fun AddSmartThings(
    navController: NavController,
    smartThingsViewModel: SmartThingsViewModel = viewModel()
) {
    Scaffold(
        topBar = { TopBar("스마트싱스", navController, AppNavItem.Main.route) },
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Desc()
                Spacer(modifier = Modifier.height(20.dp))
                AddThings(smartThingsViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                SelectThingsList()
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
    var value by remember{ mutableStateOf("")}
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(color = Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        TextField(
            value = value,
            onValueChange = { newText -> value = newText },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            textStyle = TextStyle(fontFamily = dalmoori, fontSize = 15.sp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                placeholderColor = Color.White
            ),
            placeholder = { Text("반드시 설정한 루틴 이름을 입력하세요.", fontFamily = dalmoori, fontSize = 15.sp) }
        )
    }
}

@Composable
private fun SelectThingsList(){

}

@Preview
@Composable
fun AddPreview(){
    PaymongTheme {
        Desc()
    }
}