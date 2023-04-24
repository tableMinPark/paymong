package com.paymong.domain.watch.battle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BattleFindViewModel : ViewModel() {
    var characterIdForA by mutableStateOf("")
    var characterIdForB by mutableStateOf("")

    fun reload() {
        characterIdForA = "CH100"
        characterIdForB = "CH101"
    }
}