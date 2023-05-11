package com.paymong.ui.app.help

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymong.domain.SoundViewModel
import com.paymong.domain.app.HelpViewModel
import com.paymong.ui.theme.PaymongTheme

@Composable
fun Help(
    soundViewModel: SoundViewModel,
    helpViewModel : HelpViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = helpViewModel.content, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpPreview() {
    PaymongTheme {
        Help(viewModel())
    }
}