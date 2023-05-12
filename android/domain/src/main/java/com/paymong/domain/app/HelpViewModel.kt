package com.paymong.domain.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HelpViewModel : ViewModel() {

    var content by mutableStateOf("")

    init {
        content = "도움말 페이지"
    }

    override fun onCleared() {
        super.onCleared()
    }
}