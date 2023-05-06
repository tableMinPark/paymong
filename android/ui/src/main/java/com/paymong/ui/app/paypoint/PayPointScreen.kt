package com.paymong.ui.app.paypoint

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.domain.app.PayPointViewModel
import com.paymong.ui.theme.PaymongTheme
import com.paymong.common.R
import com.paymong.common.navigation.AppNavItem
import com.paymong.ui.app.component.BgGif
import com.paymong.ui.theme.dalmoori

@Composable
fun PayPoint(
    navController: NavController,
    payPointViewModel: PayPointViewModel = viewModel()
) {
    BgGif()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { navController.navigate(AppNavItem.Main.route){
                    popUpTo("main"){
                        inclusive = true
                    }
                } }
            ),
        contentAlignment = Alignment.Center
    ) {
        PayCard(payPointViewModel)
    }
}

@Composable
fun PointInfo(text:String, point: Int){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, fontFamily = dalmoori, fontSize = 20.sp,color = Color.White)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val stringPoint = if(point>0) { "+ $point" }
                              else{ "- ${point*-1}" }
            Spacer(modifier = Modifier.width(5.dp))
            Text(stringPoint, fontFamily = dalmoori, fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.width(5.dp))
            Image(painterResource(R.drawable.pointlogo), contentDescription = null
                , modifier = Modifier.size(15.dp))
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun PayCard(payPointViewModel: PayPointViewModel) {
    Card(elevation = 10.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp, 100.dp),
        shape = RoundedCornerShape(30.dp),
        backgroundColor = Color.White.copy(alpha = 0.5f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp, 40.dp)
        ) {
            Text(text = "사용 내역"
                , fontFamily = dalmoori, fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(40.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.White))
            Spacer(modifier = Modifier.height(40.dp))
            LazyColumn(){
                items(payPointViewModel.payList.size){index ->
                    PointInfo(text = payPointViewModel.payList[index].content, point = payPointViewModel.payList[index].price)
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun PayPointPreview() {
    val navController = rememberNavController()
    PaymongTheme {
//        PayPoint(navController)
        PointInfo("페이 결제", -240)
    }
}