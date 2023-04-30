package com.paymong.domain.app.collect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CollectPayMongViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {

    private lateinit var load : Job
    lateinit var memberId : String
    var mongList = mutableListOf<String>()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            memberId = stateHandle.get<String>("memberId")
                ?: throw IllegalStateException("No memberId was passed to destination.")

            println(memberId)

            for(i in 0..5){
                val index = i.toString()
                mongList.add("CH00${index}")
            }
            for(i in 0..2){
                val index = i.toString()
                mongList.add("CH10${index}")
            }
            for(i in 0..3){
                val index = i.toString()
                mongList.add("CH20${index}")
            }
            for(i in 0..3){
                val index = i.toString()
                mongList.add("CH30${index}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
