package com.paymong.domain.app.paypoint

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PayPointViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {

    private lateinit var load : Job
    private lateinit var memberId : String

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            memberId = stateHandle.get<String>("memberId")
                ?: throw IllegalStateException("No memberId was passed to destination.")
            println(memberId)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}