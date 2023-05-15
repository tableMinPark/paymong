package com.paymong.domain.app

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.paymong.common.code.MongCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.common.code.ThingsCode
import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.response.*
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.Mong
import com.paymong.domain.watch.socket.ManagementSocketService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class AppViewModel(application: Application) : AndroidViewModel(application) {
    // 몽 생성
    var mongname by mutableStateOf("")
    var mongsleepStart by mutableStateOf("")
    var mongsleepEnd by mutableStateOf("")

    // 메인화면
    var point by mutableStateOf(0)
    var mapCode by mutableStateOf(MapCode.MP000)
    var thingsCode by mutableStateOf(ThingsCode.ST999)
    var isClick by mutableStateOf(false)

    // 몽 정보
    var mong by mutableStateOf(Mong())
    var undomong by mutableStateOf(MongCode.CH000)
    var stateCode by mutableStateOf(MongStateCode.CD000)
    var poopCount by mutableStateOf(0)
    var isHappy by mutableStateOf(false)

    var retry by mutableStateOf(false)

    // socket
    private lateinit var socketJob : Job
    private lateinit var managementSocketService: ManagementSocketService

    private val memberRepository: MemberRepository = MemberRepository()
    private val informationRepository: InformationRepository = InformationRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()

    // 메인화면 진입시 초기화
    init {
        viewModelScope.launch(Dispatchers.Main) {
            setSocket()
            findMong()
            findPoint()
        }
    }

    private fun setSocket() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                managementSocketService = ManagementSocketService()
                managementSocketService.init(listener)
            } catch (_: Exception) {}
        }
    }

    private val listener: WebSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val managementResDto = Gson().fromJson(text, RealTimeResDto::class.java)

                if (managementResDto.code != "201") {
                    Log.d("socket", managementResDto.toString())
                    when(managementResDto.code) {
                        "200", "202", "203", "204", "205", "206", "207", "208" -> {
                            val managementRealTimeResDto = Gson().fromJson(text, ManagementRealTimeResDto::class.java)
                            stateCode = MongStateCode.valueOf(managementRealTimeResDto.stateCode)
                            poopCount = managementRealTimeResDto.poopCount
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
                        "211" -> {
                            val payPointRealTimeResDto = Gson().fromJson(text, PayPointRealTimeResDto::class.java)
                            Log.d("socket", payPointRealTimeResDto.toString())
                            point = payPointRealTimeResDto.point
                        }
                        else -> {}
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 몽 생성
    fun addMong(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.addMong(AddMongReqDto(mongname,mongsleepStart,mongsleepEnd))
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mong = Mong(
                        data.mongId,
                        data.name,
                        MongCode.valueOf(data.mongCode)
                    )
                    stateCode = MongStateCode.CD000
                }
        }
    }
    // 몽 정보
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

    // 포인트
    private fun findPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    point = data.point
                }
        }
    }

    //진화
    fun evolution(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.evolution()
                .catch {
                    it.printStackTrace()
                }
                .collect{ data->
                    Log.d("evolution", data.toString())
                    undomong = mong.mongCode
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    mong = Mong(
                        mong.mongId,
                        mong.name,
                        MongCode.valueOf(data.mongCode)
                    )
                }
        }
    }

    //졸업
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

    fun stroke(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.stroke()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                        data ->
                    if(data.code == "200"){
                        isHappy = true
                        delay(1500)
                        isHappy = false
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            managementSocketService.disConnect()
            socketJob.cancel()
        } catch (_: Exception) {  }
    }
}