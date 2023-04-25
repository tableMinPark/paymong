package com.paymong.domain.app.condition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ConditionViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {
    var name by mutableStateOf("")
    var health by mutableStateOf(0.0F)
    var satiety by mutableStateOf(0.0F)
    var strength by mutableStateOf(0.0F)
    var sleep by mutableStateOf(0.0F)

    private lateinit var load : Job
    private lateinit var characterId : String

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