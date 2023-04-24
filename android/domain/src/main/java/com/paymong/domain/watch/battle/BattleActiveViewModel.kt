package com.paymong.domain.watch.battle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BattleActiveViewModel : ViewModel() {
    // 고정 상수
    private var order = ""

    // 초기 상수값
    var totalTurn by mutableStateOf(0)
    var characterIdForA by mutableStateOf("")
    var characterIdForB by mutableStateOf("")

    var nowTurn by mutableStateOf(0)
    var healthA by mutableStateOf(0.0)
    var healthB by mutableStateOf(0.0)
    var damageA by mutableStateOf(0.0)
    var damageB by mutableStateOf(0.0)

    fun init() {
        order = "A"
        totalTurn = 10
        characterIdForA = "CH100"
        characterIdForB = "CH101"
    }

    
}