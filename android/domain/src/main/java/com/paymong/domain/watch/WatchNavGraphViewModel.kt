package com.paymong.domain.watch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WatchNavGraphViewModel : ViewModel() {

    var characterId by mutableStateOf("")

    init {
        characterId = "testCharacter"
    }


}