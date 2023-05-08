package com.paymong.ui.app.main

import android.media.SoundPool
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.common.R
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MapCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.app.AppViewModel
import com.paymong.ui.theme.*
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.watch.main.Poops
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Composable
fun Main(
    navController: NavController,
    appViewModel: AppViewModel,
) {
    appViewModel.mainInit()

    // 배경
    val findBgCode = appViewModel.mapCode
    val bg = painterResource(findBgCode.code)

    if(findBgCode == MapCode.MP000){
        BgGif()
    } else {
        Image(painter = bg, contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight())
    }
    // state 는 모두 공통 코드화 시켜야 함
    val characterState = remember { mutableStateOf(CharacterCode.CH444) }

    Top(navController, appViewModel)
    MakeEgg(navController, appViewModel)
    Btn(navController, appViewModel)
}

@Composable
fun Help(navController: NavController){

    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)

    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }


    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {ButtonSoundPlay();
                        navController.navigate(AppNavItem.Help.route) }
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
    appViewModel: AppViewModel,
    navController: NavController
){
    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)

    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }

    Box(
        contentAlignment = Alignment.Center
    ){
        val btn1Bg = painterResource(R.drawable.btn_1_bg)
        Image(painter = btn1Bg, contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {ButtonSoundPlay();
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
    appViewModel: AppViewModel,
    navController: NavController
){

    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)

    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }
    Box(
        contentAlignment = Alignment.Center
    ){
        Image(painterResource(R.drawable.things), contentDescription = null,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {ButtonSoundPlay();
                        navController.navigate(AppNavItem.SmartThings.route)
                    }
                )
        )
    }
}

@Composable
fun Point(
    navController: NavController,
    appViewModel: AppViewModel
){

    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)

    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }

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
                    onClick = {ButtonSoundPlay(); navController.navigate(AppNavItem.PayPoint.route) }
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
                color = Color.Black, overflow = TextOverflow.Ellipsis, maxLines = 1
            )
        }
    }
}

@Composable
fun Top(
    navController: NavController,
    appViewModel: AppViewModel
){
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
            Info(appViewModel, navController)
            Things(appViewModel, navController)
        }
        Point(navController, appViewModel)
    }
}

@Composable
fun NicknameDialog(
    value: String,
    setShowDialog: (Boolean) -> Unit,
    setShowSleepDialog: (Boolean) -> Unit,
    setValue: (String) -> Unit,
    appViewModel: AppViewModel
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
                        if (name.value.isEmpty()) {
                            noNickname.value = "닉네임 입력은 필수에요🥺"
                            Log.d("error",noNickname.value)
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
    appViewModel: AppViewModel
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
                        appViewModel.mongsleepStart = newTime.toString()
                    }
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
    appViewModel: AppViewModel
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
                    modifier = Modifier.padding(bottom = 20.dp),
                    color = Color.Black
                )
                TimePicker(
                    time = LocalDateTime.now().toLocalTime(),
                    onTimeSelected = { newTime ->
                        setWakeValue(newTime)
                        Log.d("wakeTime",newTime.toString())
                        setShowWakeDialog(false)
                        appViewModel.mongsleepEnd = newTime.toString()
                        appViewModel.addMong()
                    }
                )
            }
        }
    }
}

@Composable
fun MakeEgg(
    navController: NavController,
    appViewModel: AppViewModel
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
            appViewModel
        )
    }

    if(sleepDialogOpen.value){
        SleepDialog(
            setSleepValue = { selectedTime.value },
            setShowSleepDialog = { sleepDialogOpen.value = it },
            setShowWakeDialog = { wakeDialogOpen.value = it },
            name.value,
            appViewModel
        )
    }
    if(wakeDialogOpen.value){
        WakeDialog(
            setWakeValue = { selectedTime.value },
            setShowWakeDialog = { wakeDialogOpen.value = it },
            name.value,
            appViewModel
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val code = appViewModel.mong.mongCode.code.split("CH")[1].toInt()

        if (code >= 400) {
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
        } else {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center,) {
                    if (appViewModel.eggTouchCount > 10) {
                        Text(
                            text = "성장을 위해\n화면을 터치해주세요.",
                            textAlign = TextAlign.Center,
                            lineHeight = 50.sp,
                            fontFamily = dalmoori,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Log.d("성장", "10번 클릭 넘엇서!")
                        CreateImageList()
                    } else {
                        Text(
                            text = " ${appViewModel.eggTouchCount}\n ",
                            lineHeight = 50.sp,
                            fontSize = 20.sp,
                            fontFamily = dalmoori,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
//                    Image(painter = painterResource(appViewModel.mong.mongCode.resourceCode),
                    Image(painter = painterResource(R.drawable.ch001),
                        contentDescription = null,
                        modifier = Modifier
                            .height(250.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    appViewModel.eggTouchCount++
                                }
                            )
                    )
                    CreateImageList()
                    val poopCount = appViewModel.poopCount
                    val poopSize = 60

                    if (poopCount == 1) {
                        Poops(0, 300, 150, 0, poopSize)
                    } else if (poopCount == 2) {
                        Poops(0, 300, 150, 0, poopSize)
                        Poops(280, 0, 200, 0, poopSize)
                    } else if (poopCount == 3) {
                        Poops(0, 300, 150, 0, poopSize)
                        Poops(280, 0, 200, 0, poopSize)
                        Poops(150, 0, 300, 0, poopSize)
                    } else if (poopCount == 4) {
                        Poops(0, 300, 150, 0, poopSize)
                        Poops(280, 0, 200, 0, poopSize)
                        Poops(150, 0, 300, 0, poopSize)
                        Poops(0, 180, 320, 0, poopSize)
                    }

                }
            }
        }
    }
}

@Composable
fun Btn(navController: NavController,
        appViewModel: AppViewModel){


    val soundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()
    val context = LocalContext.current
    val buttonSound = soundPool.load(context, com.paymong.ui.R.raw.button_sound, 1)

    fun ButtonSoundPlay () {
        soundPool.play(buttonSound, 0.5f, 0.5f, 1, 0, 1.0f)
    }

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
                        onClick = {ButtonSoundPlay();
                            navController.navigate(AppNavItem.Collect.route) }
                    )
                    .width(150.dp)
            )
            Text(
                text = "몽집", textAlign = TextAlign.Center,
                fontFamily = dalmoori, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Color.White
            )
        }
        if (appViewModel.mong.mongCode.code != "CH444"){ // chcode>=ch100일때만 나오도록 변경
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
                            onClick = {ButtonSoundPlay();
                                navController.navigate(AppNavItem.Condition.route) }
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

@Preview(showBackground = false)
@Composable
fun InfoPreview() {
    val navController = rememberNavController()
    PaymongTheme {
//        MainUI(navController, viewModel)
//        SleepDialog(setSleepValue = {LocalDateTime.now()}, setShowSleepDialog = { true },setShowWakeDialog = {false}, "sub" )
//        Top(navController)
    }
}


@Composable
fun CreateImageList() {
    val imageList = listOf(R.drawable.create_effect_1, R.drawable.create_effect_2, R.drawable.create_effect_3,)

    Box(Modifier.fillMaxSize().padding(top=150.dp)) {
        var currentIndex by remember { mutableStateOf(0) }

        LaunchedEffect(Unit) {

            delay(800L)
            currentIndex = 0
            delay(800L)
            currentIndex = 1
            delay(800L)
            currentIndex = 2


        }

        Image(
            painter = painterResource(id = imageList[currentIndex]),
            contentDescription = null,
            modifier = Modifier.size(500.dp)
        )
    }
}