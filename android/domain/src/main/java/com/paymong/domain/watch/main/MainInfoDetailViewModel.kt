package com.paymong.domain.watch.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainInfoDetailViewModel : ViewModel() {
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var weight by mutableStateOf(0)

    private lateinit var load : Job

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            val day = 1
            val hour = 4
            val minute = 14

            name = "별빛이"
            age = String.format("%d일 %d시간 %d분", day, hour, minute)
            weight = 99
        }
    }
}