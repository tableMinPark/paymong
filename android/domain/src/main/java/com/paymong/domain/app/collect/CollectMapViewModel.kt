package com.paymong.domain.app.collect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CollectMapViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {

    var mapCode by mutableStateOf("")

    private lateinit var load : Job
    lateinit var memberId : String
    var mapList = mutableListOf<String>()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            memberId = stateHandle.get<String>("memberId")
                ?: throw IllegalStateException("No memberId was passed to destination.")

            println(memberId)

            for(i in 1..32){
                val index = i.toString()
                if(i<10){
                    mapList.add("MP00${index}")
                } else{
                    mapList.add("MP0${index}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
