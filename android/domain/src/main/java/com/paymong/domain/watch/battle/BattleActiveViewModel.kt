package com.paymong.domain.watch.battle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BattleActiveViewModel : ViewModel() {
    // 고정 상수
    var order = "A"

    // 초기 상수값
    var totalTurn by mutableStateOf(10)
    var characterIdForA by mutableStateOf("CH102")
    var characterIdForB by mutableStateOf("CH100")

    var nowTurn by mutableStateOf(0)
    var healthA by mutableStateOf(0.0)
    var healthB by mutableStateOf(0.0)
    var damageA by mutableStateOf(0.0)
    var damageB by mutableStateOf(0.0)

    fun init() {
        order = "A"
        totalTurn = 10
        characterIdForA = "CH102"
        characterIdForB = "CH100"
    }


}