package com.paymong.domain.watch.battle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BattleEndViewModel : ViewModel() {
    var characterCode by mutableStateOf("")
    var win by mutableStateOf(false)

    private lateinit var load : Job
    init{
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            characterCode = "CH100"
            win = false
        }
    }
}