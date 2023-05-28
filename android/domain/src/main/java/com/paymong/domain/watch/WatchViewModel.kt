package com.paymong.domain.watch

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.paymong.common.code.MongCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.common.code.SocketCode
import com.paymong.common.code.ThingsCode
import com.paymong.data.model.response.*
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.Mong
import com.paymong.domain.entity.MongInfo
import com.paymong.domain.entity.MongStats
import com.paymong.data.socket.ManagementSocketService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WatchViewModel (
    application: Application
): AndroidViewModel(application) {
    var findMongLoadingState by mutableStateOf(false)
    var findMongConditionLoadingState by mutableStateOf(false)
    var findMongInfoLoadingState by mutableStateOf(false)
    var findPayPointLoadingState by mutableStateOf(false)
    var socketState by mutableStateOf(SocketCode.LOADING)
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
    var mapCode by mutableStateOf(MapCode.MP037)
    var thingsCode by mutableStateOf(ThingsCode.ST999)

    var isHappy by mutableStateOf(false)
    var isNavToMainClick by mutableStateOf(false)
    var eating by mutableStateOf(false)

    // 몽 진화
    var evolutionisClick by mutableStateOf(false)

    // socket
    private lateinit var managementSocketService: ManagementSocketService

    private val memberRepository: MemberRepository = MemberRepository()
    private var informationRepository: InformationRepository = InformationRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()

    fun init() {
        viewModelScope.launch(Dispatchers.Main) {
            findMong()
            findMongCondition()
            findMongInfo()
            findPayPoint()
        }
    }

    // socket
    fun socketConnect() {
        managementSocketService = ManagementSocketService()
        managementSocketService.init(listener)
    }

    fun disConnectSocket() {
        managementSocketService.disConnect()
        socketState = SocketCode.DISCONNECT
    }

    private val listener: WebSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            socketState = SocketCode.CONNECT
        }
        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            socketState = SocketCode.DISCONNECT
        }
        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            socketState = SocketCode.DISCONNECT
        }
        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val managementResDto = Gson().fromJson(text, RealTimeResDto::class.java)
                val gson = managementSocketService.getGsonInstance()

                if (managementResDto.code != "201") {
                    when(managementResDto.code) {
                        "200", "202", "203", "204", "205", "206", "207", "208" -> {
                            val managementRealTimeResDto = gson.fromJson(text, ManagementRealTimeResDto::class.java)
                            stateCode = MongStateCode.valueOf(managementRealTimeResDto.stateCode)
                            poopCount = managementRealTimeResDto.poopCount

                            val weight = managementRealTimeResDto.weight
                            val health = managementRealTimeResDto.health
                            val satiety = managementRealTimeResDto.satiety
                            val strength = managementRealTimeResDto.strength
                            val sleep = managementRealTimeResDto.sleep
                            val mongId = managementRealTimeResDto.mongId
                            val mongName = managementRealTimeResDto.name
                            val mongCode = managementRealTimeResDto.mongCode
                            val born = managementRealTimeResDto.born

                            mong = Mong(
                                mongId,
                                mongName,
                                MongCode.valueOf(mongCode)
                            )

                            mongInfo = MongInfo(
                                weight,
                                born
                            )
                            mongStats = MongStats(
                                mongId,
                                mongName,
                                health.toFloat(),
                                satiety.toFloat(),
                                strength.toFloat(),
                                sleep.toFloat()
                            )
                            findMongLoadingState = true
                            findMongInfoLoadingState = true
                            findMongConditionLoadingState = true
                        }
                        "209" -> {
                            val mapRealTimeResDto = gson.fromJson(text, MapRealTimeResDto::class.java)
                            mapCode = MapCode.valueOf(mapRealTimeResDto.mapCode)
                        }
                        "210" -> {
                            val thingsRealTimeResDto = gson.fromJson(text, ThingsRealTimeResDto::class.java)
                            thingsCode = ThingsCode.valueOf(thingsRealTimeResDto.thingsCode)
                        }
                        "211" -> {
                            val payPointRealTimeResDto = gson.fromJson(text, PayPointRealTimeResDto::class.java)
                            point = payPointRealTimeResDto.point
                            findPayPointLoadingState = true
                        }
                        "300" -> {
                            webSocket.send("connect")
                        }
                        else -> {}
                    }
                }
            } catch (_: Exception) {}
        }
    }
    // mong
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
    // api
    private suspend fun findMong() {
        informationRepository.findMong()
            .catch {
                it.printStackTrace()
            }
            .collect { data ->
                // 첫 몽 생성하는 경우 (기존의 mongId가 없음)
                mong = Mong(
                    data.mongId,
                    data.name,
                    MongCode.valueOf(data.mongCode)
                )
                poopCount = data.poopCount
                if (data.stateCode != "") {
                    stateCode = MongStateCode.valueOf(data.stateCode)
                }
                if (data.mapCode != "") {
                    mapCode = MapCode.valueOf(data.mapCode)
                }
                findMongLoadingState = true
            }
    }
    private suspend fun findMongCondition() {
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
                    findMongConditionLoadingState = true
                }
        }
    }
    private suspend fun findMongInfo() {
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
                    age = calcAge()
                    findMongInfoLoadingState = true
                }
        }
    }
    private suspend fun findPayPoint(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect{ data ->
                    point = data.point
                    findPayPointLoadingState = true
                }
        }
    }

    private fun calcAge(): String {
        var ageStr = ""
        try {
            val day = ChronoUnit.DAYS.between(mongInfo.born, LocalDateTime.now())
            val hours = ChronoUnit.HOURS.between(mongInfo.born.plusDays(day), LocalDateTime.now())
            val minutes = ChronoUnit.MINUTES.between(
                mongInfo.born.plusDays(day).plusHours(hours),
                LocalDateTime.now()
            )
            ageStr = String.format("%d일 %d시간 %d분", day, hours, minutes)
        } catch (_: Exception) {}
        return ageStr
    }
    fun stroke(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.stroke()
                .catch {
                    it.printStackTrace()
                }
                .collect{ data ->
                    if(data.code == "200"){
                        isHappy = true
                        delay(2000)
                        isHappy = false
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
            undomong = mong.mongCode
            managementRepository.evolution()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                    evolutionisClick = true
                    delay(2000)
                    evolutionisClick = false
                }
        }
    }
    fun graduation(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.graduation()
                .catch {
                    it.printStackTrace()
                }
                .collect{}
        }
    }
}

class WatchViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WatchViewModel::class.java)){
            return WatchViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}