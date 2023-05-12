package com.paymong.domain.watch

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.paymong.common.code.MongCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.data.model.response.ManagementRealTimeResDto
import com.paymong.data.model.response.ManagementResDto
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.MemberRepository
import com.paymong.data.repository.ThingsRepository
import com.paymong.domain.entity.Mong
import com.paymong.domain.entity.MongInfo
import com.paymong.domain.entity.MongStats
import com.paymong.domain.watch.socket.ManagementSocketService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


class WatchViewModel (
    application: Application
): AndroidViewModel(application) {    
    // 포인트 정보
    var point by mutableStateOf(0)
    // 몽 기본정보 (이름, 아이디, 몽 코드)
    var mong by mutableStateOf(Mong())
    // 몽 지수
    var mongStats by mutableStateOf(MongStats())
    // 몽 정보 (몸무게, 태어난 일자)
    var undomong by mutableStateOf(MongCode.CH000)
    var mongInfo by mutableStateOf(MongInfo())
    var age by mutableStateOf("")
    // 몽 상태, 똥 갯수, 맵 코드
    var stateCode by mutableStateOf(MongStateCode.CD000)
    var poopCount by mutableStateOf(0)
    var mapCode by mutableStateOf(MapCode.MP000)

    var isHappy by mutableStateOf(false)
    var isClicked by mutableStateOf(false)
    var showtoast by mutableStateOf(false)
    var msg by mutableStateOf("")

    // 몽 진화
    var evolutionisClick by mutableStateOf(false)

    // socket
    private lateinit var socketJob : Job
    private lateinit var managementSocketService: ManagementSocketService

    private val memberRepository: MemberRepository = MemberRepository()
    private var informationRepository: InformationRepository = InformationRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            findMong()
            findMongCondition()
            findMongInfo()
            findPayPoint()
            managementSocketService = ManagementSocketService()
            managementSocketService.init(listener)
        }
    }

    // socket
    private val listener: WebSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val managementRealTimeResDto = Gson().fromJson(text, ManagementRealTimeResDto::class.java)

                Log.e("WatchViewModel", "response : $managementRealTimeResDto")

                if (managementRealTimeResDto.code != "201") {
                    if (managementRealTimeResDto.code != "209") {
                        stateCode = MongStateCode.valueOf(managementRealTimeResDto.stateCode)
                        poopCount = managementRealTimeResDto.poopCount

                        val weight = managementRealTimeResDto.weight
                        val health = managementRealTimeResDto.health
                        val satiety = managementRealTimeResDto.satiety
                        val strength = managementRealTimeResDto.strength
                        val sleep = managementRealTimeResDto.sleep

                        mongInfo = MongInfo(
                            weight,
                            mongInfo.born
                        )
                        mongStats = MongStats(
                            mongStats.mongId,
                            mongStats.name,
                            health.toFloat(),
                            satiety.toFloat(),
                            strength.toFloat(),
                            sleep.toFloat()
                        )
                    } else {
                        mapCode = MapCode.valueOf(managementRealTimeResDto.mapCode)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updateStates(managementResDto: ManagementResDto) {
        stateCode = MongStateCode.valueOf(managementResDto.stateCode)
        poopCount = managementResDto.poopCount

        val weight = managementResDto.weight
        val health = managementResDto.health
        val satiety = managementResDto.satiety
        val strength = managementResDto.strength
        val sleep = managementResDto.sleep

        mongInfo = MongInfo(
            weight,
            mongInfo.born
        )
        mongStats = MongStats(
            mongStats.mongId,
            mongStats.name,
            health.toFloat(),
            satiety.toFloat(),
            strength.toFloat(),
            sleep.toFloat()
        )
    }


    override fun onCleared() {
        super.onCleared()
        try {
            managementSocketService.disConnect()
            socketJob.cancel()
        } catch (e: Exception) {  }
    }
    private fun findPayPoint(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect{ data ->
                    delay(1000)
                    point = data.point.toInt()
                }
        }
    }
    private fun findMong() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMong()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mong = Mong(
                        data.mongId,
                        data.name,
                        MongCode.valueOf(data.mongCode)
                    )
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    poopCount = data.poopCount
                    mapCode = MapCode.valueOf(data.mapCode ?: "MP000")
                }
        }
    }
    private fun findMongCondition() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMongStats()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mongStats = MongStats(
                        data.mongId,
                        data.name,
                        data.health,
                        data.satiety,
                        data.strength,
                        data.sleep,
                    )
                }
        }
    }
    private fun findMongInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMongInfo()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mongInfo = MongInfo(
                        data.weight,
                        data.born
                    )
                }
            while(true) {
                age = calcAge()
                delay(6000)
            }
        }
    }
    private fun calcAge(): String {
        val day = ChronoUnit.DAYS.between(mongInfo.born, LocalDateTime.now())
        val hours = ChronoUnit.HOURS.between(mongInfo.born.plusDays(day), LocalDateTime.now())
        val minutes = ChronoUnit.MINUTES.between(mongInfo.born.plusDays(day).plusHours(hours), LocalDateTime.now())
        return String.format("%d일 %d시간 %d분", day, hours, minutes)
    }
    fun stroke(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.stroke()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                    data ->
                    if(data.code == "200"){
                        showtoast = false
                        isHappy = true
                    } else{
                        showtoast = true
                        msg = "쓰다듬기는 한 시간에 한 번만 가능합니다."
                    }
                }
        }
    }
    fun sleep(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.sleep()
                .catch {
                    it.printStackTrace()
                }.collect{}
        }
    }
    fun poop(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.poop()
                .catch {
                    it.printStackTrace()
                }.collect{}
        }
    }
    fun evolution(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.evolution()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                        data->
                    undomong = mong.mongCode
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    mong.mongCode = MongCode.valueOf(data.mongCode)
                }
        }
    }

    fun graduation(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.graduation()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                        data->
                    mong.mongCode = MongCode.valueOf(data.mongCode)
                }
        }
    }
}