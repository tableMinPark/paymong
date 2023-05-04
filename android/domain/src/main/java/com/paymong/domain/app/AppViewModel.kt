package com.paymong.domain.app

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.CharacterCode
import com.paymong.data.model.request.CreateReqDto
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.repository.CreateRepository
import com.paymong.data.repository.LoginRepository
import com.paymong.domain.entity.MongInfo
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var load : Job
    var poopCount by mutableStateOf(0)
    var point by mutableStateOf(0)
    var mongInfo by mutableStateOf(MongInfo())
    var mongname by mutableStateOf("")
    var mongsleepStart by mutableStateOf("")
    var mongsleepEnd by mutableStateOf("")

    private var createRepository: CreateRepository = CreateRepository()
    private var loginRepository: LoginRepository = LoginRepository()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            findPoopCount()
            findPoint()
            findMongInfo()
        }
    }

    fun loginCheck() : Boolean {
        return loginRepository.reissue()
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
            mongInfo.weight,
            mongInfo.born
        )
    }

    fun setMongName(name:String){
        mongname = name
    }

    fun setMongSleepStart(sleepStart:String){
        mongsleepStart = sleepStart
    }

    fun setMongSleepEnd(sleepEnd:String){
        mongsleepEnd = sleepEnd
        Log.d("사이",mongsleepEnd)
        create()
        Log.d("끝","끝")
    }

    fun create(){
        var res = createRepository.create(CreateReqDto(mongname,mongsleepStart,mongsleepEnd))
        Log.d("create", res.toString())
        if (res != null) {
            mongInfo = MongInfo(
                res.name,
                CharacterCode.valueOf(res.mongCode),
                res.weight,
                res.born
            )
        }
        Log.d("createRes",res.toString())
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
        mongInfo = MongInfo(name, CharacterCode.valueOf(mongCode), weight, LocalDateTime.now())
    }

    override fun onCleared() {
        super.onCleared()
    }
}