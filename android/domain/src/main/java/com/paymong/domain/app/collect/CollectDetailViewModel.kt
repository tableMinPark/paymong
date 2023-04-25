package com.paymong.domain.app.collect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CollectDetailViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {
    var collectCategory by mutableStateOf("")

    private lateinit var load : Job
    private lateinit var memberId : String

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            collectCategory = stateHandle.get<String>("collectCategory")
                ?: throw IllegalStateException("No collectCategory was passed to destination.")
            memberId = stateHandle.get<String>("memberId")
                ?: throw IllegalStateException("No memberId was passed to destination.")

            println(collectCategory)
            println(memberId)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
