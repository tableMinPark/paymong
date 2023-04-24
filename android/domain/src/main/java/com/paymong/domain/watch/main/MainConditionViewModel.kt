package com.paymong.domain.watch.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainConditionViewModel : ViewModel() {
    var health by mutableStateOf(0.0F)
    var satiety by mutableStateOf(0.0F)
    var strength by mutableStateOf(0.0F)
    var sleep by mutableStateOf(0.0F)

    init {
        health = 0.5F
        satiety = 0.2F
        strength = 0.3F
        sleep = 0.4F
    }
}