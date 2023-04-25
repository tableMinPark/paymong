package com.paymong.domain.watch.battle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BattleActiveViewModel : ViewModel() {
    // 고정 상수
    var order = "A"

    // 초기 상수값
    var totalTurn by mutableStateOf(0)
    var characterIdForA by mutableStateOf("")
    var characterIdForB by mutableStateOf("")

    var nowTurn by mutableStateOf(0)
    var count : Int = 0
    var healthA by mutableStateOf(0.0)
    var healthB by mutableStateOf(0.0)
    var damageA by mutableStateOf(0.0f)
    var damageB by mutableStateOf(0.0f)

    private lateinit var load : Job
    init {
        if (::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            order = "A"
            totalTurn = 10
            characterIdForA = "CH102"
            characterIdForB = "CH100"
            damageA = 0.1f
            damageB = 0.4f
        }
    }


}