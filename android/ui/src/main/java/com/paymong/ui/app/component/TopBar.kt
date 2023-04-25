package com.paymong.ui.app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.paymong.domain.app.component.TopBarViewModel
import com.paymong.ui.app.collect.Collect
import com.paymong.ui.theme.PaymongTheme

@Composable
fun TopBar(
    msg: String,
    navController: NavController
) {
    val viewModel: TopBarViewModel = viewModel()
    TopBarUI(msg, navController)
}


@Composable
fun TopBarUI(
    msg: String,
    navController: NavController
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onSecondary
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = msg, textAlign = TextAlign.Center)
            Button(
                onClick = { navController.popBackStack() },
            ) {
                Text(text = "뒤로가기")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val navController = rememberNavController()
    PaymongTheme {
        TopBar("MSG", navController)
    }
}