package com.paymong.ui.app.main

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.paymong.common.R
import com.paymong.common.code.*
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.AppViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.app.component.CharacterGif
import com.paymong.ui.app.component.EmotionGif
import com.paymong.ui.app.component.ThingsGif
import com.paymong.ui.watch.common.LoadingGif
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Main(
    navController: NavController,
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel
) {
    LaunchedEffect(key1 = true) {
        if (appViewModel.isSocketConnect == SocketCode.NOT_TOKEN) {
            appViewModel.isSocketConnect = SocketCode.LOADING
            appViewModel.connectSocket()
        } else if (appViewModel.isSocketConnect == SocketCode.CONNECT) {
            appViewModel.init()
        }
    }

    // 배경
    val findBgCode = appViewModel.mapCode
    val bg = painterResource(findBgCode.phoneCode)

    if(findBgCode == MapCode.MP000){
        BgGif()
    } else {
        Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight())
    }

    if (!appViewModel.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val logo = painterResource(R.drawable.app_logo)
                Image(
                    painter = logo, contentDescription = null, modifier = Modifier
                        .fillMaxWidth()
                        .padding(80.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val loadBarSize = 75
                Box(
                    modifier = Modifier
                        .width(loadBarSize.dp)
                        .height(loadBarSize.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    LoadingGif()
                }
            }
        }
    } else {
        Top(navController, appViewModel, soundViewModel)
        MakeEgg(appViewModel,soundViewModel)
        Btn(navController, appViewModel, soundViewModel)
    }

}

@Composable
fun Help(navController: NavController,
         soundViewModel: SoundViewModel
) {
    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        navController.navigate(AppNavItem.Help.route)
                    }
                )
                .height(40.dp)
                .padding(horizontal = 20.dp)
        )
        Text(text = "?", textAlign = TextAlign.Center,
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun Info(
    navController: NavController,
    soundViewModel: SoundViewModel
){
    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        navController.navigate(AppNavItem.InfoDetail.route)
                    }
                )
                .width(40.dp)
        )
        Text(text = "i", textAlign = TextAlign.Center,
            fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun Things(
    navController: NavController,
    soundViewModel: SoundViewModel
){


    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        navController.navigate(AppNavItem.Help.route)
                    }
                )
                .height(40.dp)
                .padding(horizontal = 20.dp)
        )
        Image(painterResource(R.drawable.things), contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        navController.navigate(AppNavItem.SmartThings.route)
                    }
                )
        )
    }
}

@Composable
fun Point(
    navController: NavController,
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel
){

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
                    onClick = {
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        navController.navigate(AppNavItem.PayPoint.route)
                    }
                )
                .padding(horizontal = 20.dp)
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
                text = NumberFormat.getNumberInstance(Locale.getDefault()).format(appViewModel.point),
                textAlign = TextAlign.Center,
                fontFamily = dalmoori, fontSize = 18.sp,
                color =  Color(0xFF0C4DA2), overflow = TextOverflow.Ellipsis, maxLines = 1
            )
        }
    }
}

@Composable
fun Top(
    navController: NavController,
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel

){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row (    horizontalArrangement = Arrangement.SpaceBetween){
            Help(navController, soundViewModel)
            Info(navController, soundViewModel)
            Things(navController, soundViewModel)
        }
        Point(navController, appViewModel, soundViewModel)
    }
}

@Composable
fun NicknameDialog(
    value: String,
    setShowDialog: (Boolean) -> Unit,
    setShowSleepDialog: (Boolean) -> Unit,
    setValue: (String) -> Unit,
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel
){
    val noNickname = remember { mutableStateOf("") }
    val name = remember { mutableStateOf(value) }

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
                    value = name.value,
                    onValueChange = { name.value = it },
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
                        soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                        if (name.value.isEmpty()) {
                            noNickname.value = "닉네임 입력은 필수에요🥺"
                            return@Button
                        }
                        setValue(name.value)
                        setShowDialog(false)
                        setShowSleepDialog(true)
                        appViewModel.mongname = name.value
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
    onTimeSelected: (LocalTime) -> Unit,
    soundViewModel: SoundViewModel
) {
    val hourScrollState = rememberLazyListState()
    val minuteScrollState = rememberLazyListState()

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
            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
            val selectedTime = LocalTime.of(hourScrollState.firstVisibleItemIndex, minuteScrollState.firstVisibleItemIndex * 10)
            onTimeSelected(selectedTime)
        },
        colors = ButtonDefaults.buttonColors(PayMongBlue),
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Text(text = "확인", fontFamily = dalmoori, color = Color.Black)
    }
}

@Composable
fun SleepDialog(
    setSleepValue: (LocalTime) -> Unit,
    setShowSleepDialog: (Boolean) -> Unit,
    setShowWakeDialog: (Boolean) -> Unit,
    name: String,
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel
){
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
                    text = "$name 재울 시간 💤",
                    fontFamily = dalmoori,
                    modifier = Modifier.padding(bottom = 15.dp),
                    color = Color.Black
                )
                Text(
                    text = "( 숫자를 스크롤해주세요 )",
                    fontFamily = dalmoori,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .background(color = PayMongBlue.copy(alpha = 0.4f)),
                    color = Color.Black,
                    fontSize = 13.sp
                )
                TimePicker(
                    onTimeSelected = { newTime ->
                        setSleepValue(newTime)
                        setShowSleepDialog(false)
                        setShowWakeDialog(true)
                        appViewModel.mongsleepStart = newTime.toString()
                    },
                    soundViewModel = soundViewModel

                )
            }
        }
    }
}
@Composable
fun WakeDialog(
    setWakeValue: (LocalTime) -> Unit,
    setShowWakeDialog: (Boolean) -> Unit,
    name: String,
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel
){
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
                    text = "$name 깨울 시간 ☀",
                    fontFamily = dalmoori,
                    modifier = Modifier.padding(bottom = 15.dp),
                    color = Color.Black
                )
                Text(
                    text = "( 숫자를 스크롤해주세요 )",
                    fontFamily = dalmoori,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .background(color = PayMongBlue.copy(alpha = 0.4f)),
                    color = Color.Black,
                    fontSize = 13.sp
                )
                TimePicker(
                    onTimeSelected = { newTime ->
                        setWakeValue(newTime)
                        setShowWakeDialog(false)
                        appViewModel.mongsleepEnd = newTime.toString()
                        appViewModel.addMong()
                        appViewModel.retry = false
                    },
                    soundViewModel = soundViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MakeEgg(
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel
){
    val dialogOpen = remember {mutableStateOf(false)}
    val sleepDialogOpen = remember {mutableStateOf(false)}
    val wakeDialogOpen = remember {mutableStateOf(false)}
    val selectedTime = remember { mutableStateOf(LocalDateTime.now()) }
    val name = remember{ mutableStateOf("") }

    if(dialogOpen.value){
        NicknameDialog(
            value = "",
            setShowDialog = { dialogOpen.value = it },
            setShowSleepDialog = { sleepDialogOpen.value = it },
            setValue = { name.value = it },
            appViewModel,
            soundViewModel
        )
    }

    if(sleepDialogOpen.value){
        SleepDialog(
            setSleepValue = { selectedTime.value },
            setShowSleepDialog = { sleepDialogOpen.value = it },
            setShowWakeDialog = { wakeDialogOpen.value = it },
            name.value,
            appViewModel,
            soundViewModel
        )
    }
    if(wakeDialogOpen.value){
        WakeDialog(
            setWakeValue = { selectedTime.value },
            setShowWakeDialog = { wakeDialogOpen.value = it },
            name.value,
            appViewModel,
            soundViewModel
        )
    }

    if(appViewModel.isHappy) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp)
        ) {
            Image(painterResource(R.drawable.heart), contentDescription = null, modifier = Modifier.size(30.dp))
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val code = appViewModel.mong.mongCode.code.split("CH")[1].toInt()
        val density = LocalDensity.current.density

        if (appViewModel.retry || code >= 400) { // 알 생성
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
        } else { // 알 생성 후
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center) {
                    when (appViewModel.stateCode) {
                        MongStateCode.CD007 -> { //진화대기
                            Text(
                                text = "성장을 위해\n화면을 터치해주세요.",
                                textAlign = TextAlign.Center,
                                lineHeight = 50.sp,
                                fontFamily = dalmoori,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.clickable {
                                    appViewModel.evolution()
                                }
                            )
                        }
                        MongStateCode.CD005 -> { // 죽음
                            Image(
                                painter = painterResource(R.drawable.rip),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(300.dp)
                            )
                            Text(
                                text = "다시 시작",
                                textAlign = TextAlign.Center,
                                lineHeight = 50.sp,
                                fontFamily = dalmoori,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .background(color = Color.Black.copy(alpha = 0.4f))
                                    .fillMaxWidth()
                                    .padding(vertical = 30.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            appViewModel.retry = true
                                        }
                                    )
                            )
                        }
                        MongStateCode.CD006 -> { // 졸업
                            Image(painter = painterResource(appViewModel.mong.mongCode.resourceCode),
                                contentDescription = null,
                                modifier = Modifier.size((480/density).dp)
                            )
                            GraduationEffect(appViewModel)
                        }
                        else -> {
                            if(appViewModel.evolutionisClick){
                                Image(painter = painterResource(appViewModel.undomong.resourceCode),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size((480 / density).dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                            onClick = {
                                            }
                                        )
                                )
                                CreateImageList()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    appViewModel.evolutionisClick = false
                                }, 1800)
                            } else{
                                val mongCodeNumber = appViewModel.mong.mongCode.code.split("CH")[1].toInt()

                                if(mongCodeNumber / 100 == 1){
                                    CharacterGif(appViewModel, 200)
                                    EmotionGif(appViewModel, 0, 0, 20, 60)
                                } else if(mongCodeNumber / 100 == 2){
                                    CharacterGif(appViewModel, 250)
                                    val end = mongCodeNumber % 10
                                    if(end == 1){ //2_1
                                        EmotionGif(appViewModel, 0, 45, 90, 70)
                                    } else { //2_0, 2_2
                                        EmotionGif(appViewModel, 0, 0, 90, 70)
                                    }
                                } else if(mongCodeNumber / 100 == 3){
                                    CharacterGif(appViewModel, 300)
                                    val end = mongCodeNumber % 10
                                    if(end == 1){ //3_1
                                        EmotionGif(appViewModel, 0, 110, 100, 80)
                                    } else{ //3_0, 3_2
                                        EmotionGif(appViewModel, 0, 0, 80  , 80)
                                    }
                                } else{
                                    CharacterGif(appViewModel, 200)
                                }

                                if(appViewModel.stateCode == MongStateCode.CD002){
                                    Row(modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = Color.Black.copy(0.4f))) {
                                    }
                                }

                                val poopCount = appViewModel.poopCount
                                val poopSize = 60

                                when (poopCount) {
                                    1 -> {
                                        Poops(0, 300, 150, 0, poopSize)
                                    }
                                    2 -> {
                                        Poops(0, 300, 150, 0, poopSize)
                                        Poops(280, 0, 200, 0, poopSize)
                                    }
                                    3 -> {
                                        Poops(0, 300, 150, 0, poopSize)
                                        Poops(280, 0, 200, 0, poopSize)
                                        Poops(150, 0, 300, 0, poopSize)
                                    }
                                    4 -> {
                                        Poops(0, 300, 150, 0, poopSize)
                                        Poops(280, 0, 200, 0, poopSize)
                                        Poops(150, 0, 300, 0, poopSize)
                                        Poops(0, 180, 320, 0, poopSize)
                                    }
                                }

                                when(appViewModel.thingsCode){
                                    ThingsCode.ST000 -> {
                                        ThingsGif(R.drawable.move_vacuum, 350)
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            appViewModel.thingsCode = ThingsCode.ST999
                                        }, 5000)
                                    }
                                    ThingsCode.ST001 -> {
                                        ThingsGif(R.drawable.move_door, 400)
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            appViewModel.thingsCode = ThingsCode.ST999
                                        }, 5000)
                                    }
                                    ThingsCode.ST002 -> {
                                        ThingsGif(R.drawable.charging, 200)
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            appViewModel.thingsCode = ThingsCode.ST999
                                        }, 5000)
                                    }
                                    ThingsCode.ST003 -> {
                                        ThingsGif(R.drawable.nocharging, 200)
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            appViewModel.thingsCode = ThingsCode.ST999
                                        }, 5000)
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Btn(
    navController: NavController,
    appViewModel: AppViewModel,
    soundViewModel: SoundViewModel
){

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
                        onClick = {
                            soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                            navController.navigate(AppNavItem.Collect.route)
                        }
                    )
                    .width(150.dp)
            )
            Text(
                text = "몽집", textAlign = TextAlign.Center,
                fontFamily = dalmoori, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 2.sp
            )
        }
        val code = appViewModel.mong.mongCode.code.split("CH")[1].toInt()
        if (code >= 100){
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
                            onClick = {
                                soundViewModel.soundPlay(SoundCode.MAIN_BUTTON)
                                navController.navigate(AppNavItem.Condition.route)
                            }
                        )
                        .width(150.dp)
                )
                Text(
                    text = "지수", textAlign = TextAlign.Center,
                    fontFamily = dalmoori, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 2.sp
                )
            }
        }
    }
}

@Composable
fun Poops(start: Int, end: Int, top: Int, bottom: Int, poopSize: Int){
    val poops = painterResource(R.drawable.poops)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = start.dp, end = end.dp, top = top.dp, bottom = bottom.dp)
    ) {
        Image(
            painter = poops,
            contentDescription = null,
            modifier = Modifier.size(poopSize.dp)
        )
    }
}

@Composable
fun CreateImageList() {
    val imageList = listOf(R.drawable.create_effect_1, R.drawable.create_effect_2, R.drawable.create_effect_3)

    Box {
        var currentIndex by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {
            delay(100L)
            currentIndex = 0
            delay(500L)
            currentIndex = 1
            delay(500L)
            currentIndex = 2
            delay(500L)
        }

        Image(
            painter = painterResource(id = imageList[currentIndex]),
            contentDescription = null,
            modifier = Modifier.size(500.dp)
        )
    }
}

@Composable
fun GraduationEffect(
    appViewModel: AppViewModel
) {
    val imageList = listOf(R.drawable.star_1, R.drawable.star_2, R.drawable.star_3, R.drawable.graduation)

    Box {
        var currentIndex by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {
            delay(100L)
            currentIndex = 0
            delay(500L)
            currentIndex = 1
            delay(500L)
            currentIndex = 2
            delay(500L)
            currentIndex = 3
            delay(500L)
            currentIndex = 4
            delay(400L)
        }

        if(currentIndex == 4){
            Text(
                text = "축하합니다!\n졸업을 위해\n화면을 터치해주세요.",
                textAlign = TextAlign.Center,
                lineHeight = 50.sp,
                fontFamily = dalmoori,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            appViewModel.graduation()
                        }
                    )
            )
        } else{
            Image(
                painter = painterResource(id = imageList[currentIndex]),
                contentDescription = null,
                modifier = Modifier.size(500.dp)
            )
        }
    }
}