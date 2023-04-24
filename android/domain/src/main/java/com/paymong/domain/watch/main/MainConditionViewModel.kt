package com.paymong.domain.watch.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainConditionViewModel : ViewModel() {
    var health by mutableStateOf(0.0)
    var satiety by mutableStateOf(0.0)
    var strength by mutableStateOf(0.0)
    var sleep by mutableStateOf(0.0)

    fun reload() {
        health = 10.0
        satiety = 20.0
        strength = 30.0
        sleep = 40.0
    }
}