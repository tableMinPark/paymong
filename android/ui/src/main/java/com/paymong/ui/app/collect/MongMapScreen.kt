package com.paymong.ui.app.collect

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongCode
import com.paymong.common.navigation.AppNavItem
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.CollectMapViewModel
import com.paymong.domain.app.CollectPayMongViewModel
import com.paymong.ui.app.common.TopBar
import com.paymong.ui.theme.PayMongNavy
import com.paymong.ui.theme.dalmoori
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MongMap(
    navController: NavController,
    soundViewModel: SoundViewModel,
    collectMapViewModel: CollectMapViewModel = viewModel(),
    collectPayMongViewModel: CollectPayMongViewModel = viewModel()
) {
    LaunchedEffect(true) {
        collectMapViewModel.init()
        collectPayMongViewModel.init()
    }

    Scaffold(
        topBar = { TopBar("MongMap", navController, AppNavItem.Collect.route, soundViewModel) },
        backgroundColor = PayMongNavy
    ) {
        Box(Modifier.padding(it)) {
            val context = LocalContext.current

            val mapOptions = mutableListOf<Option>()
            val mongOptions = mutableListOf<Option>()

            val mapSelectedOption = remember { mutableStateOf<Option?>(null) }
            val mongSelectedOption = remember { mutableStateOf<Option?>(null) }

            for(i in 0 until collectMapViewModel.openList.size){
                mapOptions.add(Option(i, collectMapViewModel.openList[i].name!!, collectMapViewModel.openList[i].code!!))
            }
            for(i in 0 until collectPayMongViewModel.openList.size){
                mongOptions.add(Option(i, collectPayMongViewModel.openList[i].name!!, collectPayMongViewModel.openList[i].code!!))
            }

            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(vertical = 40.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = Color.White.copy(alpha = 0.5f)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "💫 수집한 맵과 페이몽을 조합해\n사진을 저장해보세요",
                        textAlign = TextAlign.Center, color = Color.White, letterSpacing = 2.sp, lineHeight = 30.sp,
                        fontFamily = dalmoori, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "워치 배경화면으로 사용 가능해요!",
                        textAlign = TextAlign.Center, color = Color.White, letterSpacing = 2.sp,
                        fontFamily = dalmoori, fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Map", textAlign = TextAlign.Center,
                        fontFamily = dalmoori, fontSize = 30.sp, fontWeight = FontWeight.Bold,
                        color = Color.White, letterSpacing = 2.sp
                    )
                    SelectBox(mapOptions, mapSelectedOption)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "PayMong", textAlign = TextAlign.Center,
                        fontFamily = dalmoori, fontSize = 30.sp, fontWeight = FontWeight.Bold,
                        color = Color.White, letterSpacing = 2.sp
                    )
                    SelectBox(mongOptions, mongSelectedOption)
                }

                Spacer(modifier = Modifier.height(40.dp))

                var mapImg = MapCode.MP000.code
                var mongImg = MongCode.CH444.resourceCode

                if(mapSelectedOption.value!=null && mongSelectedOption.value!=null){
                    mapImg = MapCode.valueOf(mapSelectedOption.value!!.code).code
                    mongImg = MongCode.valueOf(mongSelectedOption.value!!.code).resourceCode
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            val bitmap1 = (ContextCompat.getDrawable(
                                context,
                                mapImg
                            ) as BitmapDrawable).bitmap
                            val bitmap2 = (ContextCompat.getDrawable(
                                context,
                                mongImg
                            ) as BitmapDrawable).bitmap

                            val mergedBitmap = mergeBitmaps(bitmap1, bitmap2)

                            val directory = Environment.getExternalStorageDirectory()
                                .toString() + "/Pictures/Mong"
                            val fileName = generateFileName()
                            val file = File(directory)
                            if (!file.exists()) {
                                file.mkdir()
                            }
                            val imagePath = "$directory/$fileName"

                            val stream = ByteArrayOutputStream()
                            mergedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            val bytes = stream.toByteArray()
                            // 폴더생성에서 터짐
                            val fileOutputStream = FileOutputStream(imagePath)
                            fileOutputStream.write(bytes)
                            fileOutputStream.close()

                            val contentResolver = context.contentResolver
                            val contentValues = ContentValues().apply {
                                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Mong")
                            }
                            val imageUri = contentResolver.insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                            )

                            MediaScannerConnection.scanFile(
                                context,
                                arrayOf(imagePath),
                                arrayOf("image/jpeg"),
                                null
                            )

                            Toast.makeText(context, "이미지가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .padding(horizontal = 80.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White.copy(
                                alpha = 0.5f
                            )
                        )
                    ) {
                        Text(
                            text = "저장하기",
                            textAlign = TextAlign.Center,
                            fontFamily = dalmoori,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 2.sp
                        )
                    }
                }

            }
        }
    }
}

fun mergeBitmaps(bitmap1: Bitmap?, bitmap2: Bitmap?): Bitmap {
    val width = bitmap1!!.width
    val height = bitmap1.height

    val mergedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(mergedBitmap)

    canvas.drawBitmap(bitmap1, 0f, 0f, null)
    if (bitmap2 != null) {
        canvas.drawBitmap(bitmap2, (bitmap1.width/3).toFloat(), (bitmap1.height/2).toFloat(), null)
    }

    return mergedBitmap
}

private fun generateFileName(): String {
    val timeStamp = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now())
    return "mong_$timeStamp.jpg"
}

@Composable
fun SelectBox(options: MutableList<Option>, selectedOption: MutableState<Option?>) {
    val expanded = remember { mutableStateOf(false) }

    Column(
        Modifier
            .padding(16.dp)
            .border(2.dp, Color.White, RectangleShape)) {
        Text(
            text = selectedOption.value?.label ?: "선택",
            fontFamily = dalmoori,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .clickable { expanded.value = true }
                .padding(16.dp)
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedOption.value = option
                    expanded.value = false
                }) {
                    Text(option.label)
                }
            }
        }
    }
}

data class Option(
    val id:Int,
    val label: String,
    val code: String
)
