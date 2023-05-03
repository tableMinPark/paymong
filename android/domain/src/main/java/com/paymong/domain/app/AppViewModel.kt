package com.paymong.domain.app

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.CharacterCode
import com.paymong.domain.entity.MongInfo
import com.paymong.domain.entity.MongSetting
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var load : Job
    var poopCount by mutableStateOf(0)
    var point by mutableStateOf(0)
    var mongInfo by mutableStateOf(MongInfo())
    var mongName by mutableStateOf("")
    var mongSleepStart by mutableStateOf("")
    var mongSleepEnd by mutableStateOf("")
    var mongSetting by mutableStateOf(MongSetting())

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            findPoopCount()
            findPoint()
            findMongInfo()
        }
    }

    fun getMemberId() : Long {
        return 2L
    }

    fun getMongId() : Long {
        return 1L
    }

    fun getAge() : String {
        return "나이"
    }

    fun getResourceCode() : Int {
        return mongInfo.mongCode.resourceCode
    }

    fun setMongCode(mongCode : CharacterCode) {
        mongInfo = MongInfo(
            mongInfo.name,
            mongCode,
            mongInfo.born,
            mongInfo.weight
        )
    }

    fun setMongName(name:String){
        mongName = name
    }

    fun setMongSleepStart(sleepStart:String){
        mongSleepStart = sleepStart
    }

    fun setMongSleepEnd(sleepEnd:String){
        mongSleepEnd = sleepEnd
    }

    fun setMongSetting(){
        mongSetting = MongSetting(
            mongName,
            mongSleepStart,
            mongSleepEnd
        )
    }


    private fun findPoopCount() {
        poopCount = 0
    }

    private fun findPoint() {
        point = 10000
    }

//    private fun findMongInfo() {
//        val name = "별별이"
//        val weight = 5
//        val mongCode = "CH100"
//        mongInfo = MongInfo(name, CharacterCode.valueOf(mongCode), LocalDateTime.now().minusDays(1).minusHours(1), weight)
//    }

    private fun findMongInfo() {
        val name = ""
        val weight = 0
        val mongCode = "CH444"
        mongInfo = MongInfo(name, CharacterCode.valueOf(mongCode), LocalDateTime.now(), weight)
    }

    override fun onCleared() {
        super.onCleared()
    }
}