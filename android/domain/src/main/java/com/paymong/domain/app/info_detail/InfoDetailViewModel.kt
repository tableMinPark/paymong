package com.paymong.domain.app.info_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class InfoDetailViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {
    var name by mutableStateOf("")
    var characterId by mutableStateOf("")
    var age by mutableStateOf("")
    var weight by mutableStateOf(0)

    private lateinit var load : Job

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            characterId = stateHandle.get<String>("characterId")
                ?: throw IllegalStateException("No characterId was passed to destination.")
            println(characterId)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}