package com.paymong.ui.app.main

import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.DialogHost
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.rememberNavController
import com.paymong.common.code.CharacterCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.main.MainViewModel
import com.paymong.common.R
import com.paymong.common.code.MapCode
import com.paymong.ui.theme.*
import java.text.NumberFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun Main(navController: NavController) {
    val viewModel: MainViewModel = viewModel()
    MainUI(navController, viewModel)
}

@Composable
fun Help(navController: NavController){
    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate(AppNavItem.Help.route) }
                )
                .height(40.dp)
                .padding(horizontal = 20.dp)
        )
        Text(text = "?", textAlign = TextAlign.Center,
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Info(navController: NavController){
    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate(AppNavItem.InfoDetail.route + "/characterId") }
                )
                .width(40.dp)
        )
        Text(text = "i", textAlign = TextAlign.Center,
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Point(navController: NavController){
    val viewModel : MainViewModel = viewModel()
    Box(
        contentAlignment = Alignment.Center
    ){
        val pointBg = painterResource(R.drawable.pointbackground)
        val pointLogo = painterResource(R.drawable.pointlogo)
        Image(painter = pointBg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate(AppNavItem.PayPoint.route + "/memberId") }
                )
                .width(200.dp)
        )
        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = pointLogo, contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .padding(end = 20.dp)
            )
            Text(
                text = NumberFormat.getNumberInstance(Locale.getDefault()).format(viewModel.point),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori, fontSize = 18.sp
            )
        }

    }
}

@Composable
fun Top(navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row {
            Help(navController)
            Info(navController)
        }
        Point(navController)
    }
}
@Composable
fun NicknameDialog(
    value: String, setShowDialog: (Boolean) -> Unit, setShowSleepDialog: (Boolean) -> Unit,
    characterState: MutableState<CharacterCode>,
    setValue: (String) -> Unit
){
    val noNickname = remember { mutableStateOf("") }
    val nickname = remember { mutableStateOf(value) }
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp),
            color = Color.White.copy(alpha = 0.8f)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
                ,horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "새로운 몽의 이름을 지어주세요🤗",
                    fontFamily = dalmoori,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                OutlinedTextField(
                    value = nickname.value,
                    onValueChange = { nickname.value = it },
                    textStyle = TextStyle(fontFamily = dalmoori),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PayMongBlue200,
                        unfocusedBorderColor = PayMongBlue,
                        backgroundColor = Color.White
                    )
                )

                val setSleep = remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        if (nickname.value.isEmpty()) {
                            noNickname.value = "닉네임 입력은 필수에요🥺"
                            Log.d("error",noNickname.value)
                            return@Button
                        }
                        setValue(nickname.value)
                        setShowDialog(false)
                        setShowSleepDialog(true)
//                        characterState.value = CharacterCode.CH003
                    },
                    colors = ButtonDefaults.buttonColors(PayMongBlue),
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                ) {
                    Text(text = "이름 짓기", fontFamily = dalmoori)
                }
            }
        }
    }
}

@Composable
fun SleepDialog(value: String,setShowSleepDialog: (Boolean) -> Unit, nickname: String){
    Dialog(onDismissRequest = { setShowSleepDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp),
            color = Color.White.copy(alpha = 0.8f)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "💤${nickname}은 언제 자요?",
                    fontFamily = dalmoori,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                TimePicker()
            }
        }
    }
}

@Composable
fun TimePicker(){
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
//        var pickerValue by remember { mutableStateOf<Hours>(AMPMHours(9, 12, AMPMHours.DayTime.PM )) }
//
//        HoursNumberPicker(
//            dividersColor = MaterialTheme.colors.primary,
//            value = pickerValue,
//            onValueChange = {
//                pickerValue = it
//            },
//            hoursDivider = {
//                Text(
//                    modifier = Modifier.padding(horizontal = 8.dp),
//                    textAlign = TextAlign.Center,
//                    text = "hours"
//                )
//            },
//            minutesDivider = {
//                Text(
//                    modifier = Modifier.padding(horizontal = 8.dp),
//                    textAlign = TextAlign.Center,
//                    text = "minutes"
//                )
//            }
//        )

    }
}

@Composable
fun MakeEgg(navController: NavController, characterState: MutableState<CharacterCode>){
    val viewModel : MainViewModel = viewModel()
    val dialogOpen = remember {mutableStateOf(false)}
    val nickname = remember{ mutableStateOf("") }
    val sleepDialogOpen = remember {mutableStateOf(false)}

    if(dialogOpen.value){
        NicknameDialog(value = "", setShowDialog = { dialogOpen.value = it }, setShowSleepDialog = {sleepDialogOpen.value = it}
            ,characterState ){
            nickname.value = it
            Log.d("nickname",nickname.value)
        }
    }


    if(sleepDialogOpen.value){
        SleepDialog(value = "", setShowSleepDialog = { sleepDialogOpen.value = it }, nickname.value )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isClicked = remember { mutableStateOf(false) }
        if (characterState.value == CharacterCode.CH444) {
            Text(text = "알을 생성하려면\n화면을 터치해주세요.", textAlign = TextAlign.Center, lineHeight = 50.sp,
                fontFamily = dalmoori, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        dialogOpen.value = true
                    }
                )
            )
        } else if (characterState.value != CharacterCode.CH444){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(isClicked.value){
                    Text(text = "성장을 위해\n화면을 터치해주세요.", textAlign = TextAlign.Center, lineHeight = 50.sp,
                        fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                characterState.value = CharacterCode.CH100
                                isClicked.value = false
                            }
                        )
                    )
                }
                else{
                    Text(text = " \n ", lineHeight = 50.sp, fontSize = 20.sp,)
                }
                Image(painter = painterResource(characterState.value.code), contentDescription = null,
                    modifier = Modifier
                        .height(250.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { isClicked.value = true }
                        )
                )
            }
        }
    }
}

@Composable
fun Btn(navController: NavController, characterState: MutableState<CharacterCode>){
    val viewModel : MainViewModel = viewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            val btn2Bg = painterResource(R.drawable.btn_2_bg)
            Image(painter = btn2Bg, contentDescription = null,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { navController.navigate(AppNavItem.Collect.route + "/memberId") }
                    )
                    .width(150.dp)
            )
            Text(
                text = "몽집", textAlign = TextAlign.Center,
                fontFamily = dalmoori, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White
            )
        }
        if (characterState.value != CharacterCode.CH444){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                val btn2Bg = painterResource(R.drawable.btn_2_bg)
                Image(painter = btn2Bg, contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { navController.navigate(AppNavItem.Condition.route + "/characterId") }
                        )
                        .width(150.dp)
                )
                Text(
                    text = "지수", textAlign = TextAlign.Center,
                    fontFamily = dalmoori, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
            }
        }
    }
}

@Composable
fun MainUI(
    navController: NavController,
    viewModel: MainViewModel
) {
    // 배경
    val findBgCode = viewModel.background
    val bgCode = MapCode.valueOf(findBgCode)
    val bg = painterResource(bgCode.code)
    Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight())

    // state 는 모두 공통 코드화 시켜야 함
    val characterState = remember { mutableStateOf(CharacterCode.CH444) }

    Top(navController)
    MakeEgg(navController, characterState)
    Btn(navController, characterState)
}

@Preview(showBackground = false)
@Composable
fun InfoPreview() {
    val navController = rememberNavController()
    val viewModel : MainViewModel = viewModel()
    PaymongTheme {
//        MainUI(navController, viewModel)

    }
}