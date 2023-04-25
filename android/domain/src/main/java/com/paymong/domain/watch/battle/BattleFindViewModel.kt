package com.paymong.domain.watch.battle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BattleFindViewModel : ViewModel() {
    var characterIdForA by mutableStateOf("CH102")
    var characterIdForB by mutableStateOf("CH100")

    fun reload() {
//        characterIdForA = "CH100"
        characterIdForA = "CH102"
        characterIdForB = "CH100"
    }
}