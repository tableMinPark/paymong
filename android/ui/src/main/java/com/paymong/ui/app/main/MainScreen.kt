package com.paymong.ui.app.main

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.size.OriginalSize
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MapCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.main.MainViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.app.component.BgGif
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
                ).padding(horizontal = 20.dp)
        )
        Row(
            modifier = Modifier.width(100.dp),
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
                fontFamily = dalmoori, fontSize = 18.sp,
                color = Color.Black, overflow = TextOverflow.Ellipsis, maxLines = 1
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
                    modifier = Modifier.padding(bottom = 20.dp),
                    color = Color.Black
                )

                OutlinedTextField(
                    value = nickname.value,
                    onValueChange = { nickname.value = it },
                    textStyle = TextStyle(fontFamily = dalmoori, color = Color.Black),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PayMongBlue200,
                        unfocusedBorderColor = PayMongBlue,
                        backgroundColor = Color.White
                    )
                )

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
                    Text(text = "이름 짓기", fontFamily = dalmoori, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun TimePicker(
    time: LocalTime,
    onTimeSelected: (LocalTime) -> Unit
) {
    val hour by remember { mutableStateOf(time.hour) }
    val minute by remember { mutableStateOf(time.minute/10*10) }

    val hourScrollState = rememberLazyListState()
    val hourSelected= remember { mutableStateOf(hour) }
    val minuteScrollState = rememberLazyListState()
    val minuteSelected= remember { mutableStateOf(minute) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = Modifier.size(24.dp), horizontalAlignment = Alignment.CenterHorizontally, state = hourScrollState,
            ){
                items(24){index ->
                    var number = index.toString()
                    if(index/10 == 0){ number = String.format("0${index}") }
                    Text(number, fontFamily = dalmoori, fontSize = 20.sp, modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
                }
            }
        }
        Text(text = ":", fontFamily = dalmoori, modifier = Modifier.padding(horizontal = 10.dp), fontSize = 20.sp, color = Color.Black)
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = Modifier.size(24.dp), horizontalAlignment = Alignment.CenterHorizontally, state = minuteScrollState
            ){
                items(6){index ->
                    var number = (index * 10).toString()
                    if(number == "0"){ number = "00" }
                    Text(number, fontFamily = dalmoori, fontSize = 20.sp, modifier = Modifier.padding(vertical = 2.dp), color = Color.Black)
                }
            }
        }
    }
    Button(
        onClick = {
            val selectedTime = LocalTime.of(hourScrollState.firstVisibleItemIndex, minuteScrollState.firstVisibleItemIndex*10)
            onTimeSelected(selectedTime)
        },
        colors = ButtonDefaults.buttonColors(PayMongBlue),
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Text(text = "확인", fontFamily = dalmoori, color = Color.Black)
    }
}

@Composable
fun SleepDialog(setSleepValue: (LocalTime) -> Unit, setShowSleepDialog: (Boolean) -> Unit, setShowWakeDialog: (Boolean) -> Unit, nickname: String){
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
                    text = "${nickname} 재울 시간 💤",
                    fontFamily = dalmoori,
                    modifier = Modifier.padding(bottom = 20.dp),
                    color = Color.Black
                )
                TimePicker(
                    time = LocalDateTime.now().toLocalTime(),
                    onTimeSelected = { newTime ->
                        setSleepValue(newTime)
                        Log.d("sleepTime",newTime.toString())
                        setShowSleepDialog(false)
                        setShowWakeDialog(true)
                    }
                )
            }
        }
    }
}
@Composable
fun WakeDialog(setWakeValue: (LocalTime) -> Unit, setShowWakeDialog: (Boolean) -> Unit, nickname: String, characterState: MutableState<CharacterCode>){
    Dialog(onDismissRequest = { setShowWakeDialog(false) }) {
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
                    text = "${nickname} 깨울 시간 ☀",
                    fontFamily = dalmoori,
                    modifier = Modifier.padding(bottom = 20.dp),
                    color = Color.Black
                )
                TimePicker(
                    time = LocalDateTime.now().toLocalTime(),
                    onTimeSelected = { newTime ->
                        setWakeValue(newTime)
                        Log.d("wakeTime",newTime.toString())
                        characterState.value = CharacterCode.CH003
                        setShowWakeDialog(false)
                    }
                )
            }
        }
    }
}

@Composable
fun MakeEgg(navController: NavController, characterState: MutableState<CharacterCode>){
    val viewModel : MainViewModel = viewModel()
    val dialogOpen = remember {mutableStateOf(false)}
    val nickname = remember{ mutableStateOf("") }
    val sleepDialogOpen = remember {mutableStateOf(false)}
    val wakeDialogOpen = remember {mutableStateOf(false)}
    val selectedTime = remember { mutableStateOf(LocalDateTime.now()) }

    if(dialogOpen.value){
        NicknameDialog(value = "", setShowDialog = { dialogOpen.value = it }, setShowSleepDialog = {sleepDialogOpen.value = it}
            ,characterState ){
            nickname.value = it
            Log.d("nickname",nickname.value)
        }
    }

    if(sleepDialogOpen.value){
        SleepDialog(setSleepValue = {selectedTime.value}, setShowSleepDialog = { sleepDialogOpen.value = it }, setShowWakeDialog = { wakeDialogOpen.value = it }
            , nickname.value )
    }
    if(wakeDialogOpen.value){
        WakeDialog(setWakeValue = {selectedTime.value}, setShowWakeDialog = { wakeDialogOpen.value = it }, nickname.value, characterState )
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
    if(findBgCode == "MP000"){
        BgGif()
    } else {
        Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight())
    }
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
//        SleepDialog(setSleepValue = {LocalDateTime.now()}, setShowSleepDialog = { true },setShowWakeDialog = {false}, "sub" )
        Top(navController)
    }
}