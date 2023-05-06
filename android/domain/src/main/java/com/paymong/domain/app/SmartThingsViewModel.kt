package com.paymong.domain.app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.paymong.domain.entity.Things
import java.time.LocalDateTime

class SmartThingsViewModel : ViewModel() {
    var thingsList = mutableListOf<Things>()
    var routine = mutableStateOf("")

    fun things(){
        thingsList.clear()
        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
    }
}