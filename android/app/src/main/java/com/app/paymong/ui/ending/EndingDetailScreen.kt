package com.app.paymong.ui.ending

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.paymong.ui.component.TopBar
import com.app.paymong.ui.theme.PaymongTheme

@Composable
fun EndingDetail(
    characterId: String,
    navController: NavController
) {
    Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center
) {
        Scaffold(
            topBar = { TopBar(msg = characterId, navController = navController) }
        ) {
            Box(Modifier.padding(it)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "컬렉션 상세 for $characterId", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndingDetailPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        EndingDetail("캐릭터_ID", navController)
    }
}