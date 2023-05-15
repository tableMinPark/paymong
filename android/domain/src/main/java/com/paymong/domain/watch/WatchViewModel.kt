package com.paymong.domain.watch

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.paymong.common.code.MongCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.common.code.ThingsCode
import com.paymong.data.model.response.*
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.MemberRepository
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
    var thingsCode by mutableStateOf(ThingsCode.ST999)

    var isHappy by mutableStateOf(false)
    var isClicked by mutableStateOf(false)
    var eating by mutableStateOf(false)

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
            setSocket()
            findMong()
            findMongCondition()
            findMongInfo()
            findPayPoint()
        }
    }
    override fun onCleared() {
        super.onCleared()
        try {
            managementSocketService.disConnect()
            socketJob.cancel()
        } catch (_: Exception) {}
    }

    // socket
    private fun setSocket() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                managementSocketService = ManagementSocketService()
                managementSocketService.init(listener)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private val listener: WebSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val managementResDto = Gson().fromJson(text, RealTimeResDto::class.java)

                if (managementResDto.code != "201") {
                    Log.d("socket", managementResDto.toString())
                    when(managementResDto.code) {
                        "200" -> {
                            val managementRealTimeResDto = Gson().fromJson(text, ManagementRealTimeResDto::class.java)
                            Log.d("socket", managementRealTimeResDto.toString())
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
                        }
                        "209" -> {
                            val mapRealTimeResDto = Gson().fromJson(text, MapRealTimeResDto::class.java)
                            Log.d("socket", mapRealTimeResDto.toString())
                            mapCode = MapCode.valueOf(mapRealTimeResDto.mapCode)
                        }
                        "210" -> {
                            val thingsRealTimeResDto = Gson().fromJson(text, ThingsRealTimeResDto::class.java)
                            Log.d("socket", thingsRealTimeResDto.toString())
                            thingsCode = ThingsCode.valueOf(thingsRealTimeResDto.thingsCode)
                        }
                        else -> {}
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
    private fun findPayPoint(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect{ data ->
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
                    mapCode = MapCode.valueOf(data.mapCode)
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
                    age = calcAge()
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
                    // 쓰다듬기 성공
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
            managementRepository.evolution()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                        data->
                    undomong = mong.mongCode
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    mong = Mong(
                        mong.mongId,
                        mong.name,
                        MongCode.valueOf(data.mongCode),
                    )
                    delay(1800)
                    evolutionisClick = true
                }
        }
    }
    fun graduation(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.graduation()
                .catch {
                    it.printStackTrace()
                }
                .collect{data->
                    mong = Mong(
                        mong.mongId,
                        mong.name,
                        MongCode.valueOf(data.mongCode),
                    )
                }
        }
    }
}