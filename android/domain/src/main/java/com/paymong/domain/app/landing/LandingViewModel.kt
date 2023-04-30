package com.paymong.domain.app.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LandingViewModel : ViewModel() {
    var id by mutableStateOf("")
    private lateinit var load : Job
    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            id = "subin"
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}