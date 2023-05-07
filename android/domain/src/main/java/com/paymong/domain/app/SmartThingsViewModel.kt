package com.paymong.domain.app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.paymong.domain.entity.AddThings
import com.paymong.domain.entity.Things
import java.time.LocalDateTime

class SmartThingsViewModel : ViewModel() {
    var connectedThingsList = mutableListOf<Things>()
    var thingsList = mutableListOf<AddThings>()
    var isSelect = mutableStateOf(false)
    var routine = mutableStateOf("")

    fun connectedThings(){
        connectedThingsList.clear()
        connectedThingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
//        thingsList.add(Things(0,"","청소기", "청소하기", LocalDateTime.now()))
    }

    fun toConnectThings(){
        thingsList.clear()
        thingsList.add(AddThings("청소기", isSelect.value))
        thingsList.add(AddThings("문 열림 센서", isSelect.value))
        thingsList.add(AddThings("버튼", isSelect.value))
    }
}