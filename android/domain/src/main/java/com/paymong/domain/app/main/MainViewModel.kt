package com.paymong.domain.app.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var characterCode by mutableStateOf("")
    var background by mutableStateOf("")
    var point by mutableStateOf(0)
    var poopCount by mutableStateOf(0)

    private lateinit var load : Job
    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            characterCode = "CH003"
            background = "MP000"
            point = 10000
            poopCount = 1
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}