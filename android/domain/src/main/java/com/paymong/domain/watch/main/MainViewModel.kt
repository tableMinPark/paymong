package com.paymong.domain.watch.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var background by mutableStateOf("")

    private lateinit var load : Job

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            println("MainViewMOdel 시작")
            background = "MP000"
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("MainViewMOdel 끝")
    }
}