package com.paymong.domain.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var background by mutableStateOf("")
    var eggTouchCount by mutableStateOf(0)

    init {
        background = "MP000"
        eggTouchCount = 0
    }

    override fun onCleared() {
        super.onCleared()
    }
}