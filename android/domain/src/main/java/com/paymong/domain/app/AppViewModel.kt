package com.paymong.domain.app

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.paymong.common.code.*
import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.response.*
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.Mong
import com.paymong.data.socket.ManagementSocketService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class AppViewModel(
    application: Application
) : AndroidViewModel(application) {
    var findMongLoadingState by mutableStateOf(false)
    var findPayPointLoadingState by mutableStateOf(false)
    var socketState by mutableStateOf(SocketCode.LOADING)
    // 몽 생성
    var mongname by mutableStateOf("")
    var mongsleepStart by mutableStateOf("")
    var mongsleepEnd by mutableStateOf("")

    // 메인화면
    var point by mutableStateOf(0)
    var mapCode by mutableStateOf(MapCode.MP000)
    var thingsCode by mutableStateOf(ThingsCode.ST999)
    var evolutionisClick by mutableStateOf(false)

    // 몽 정보
    var mong by mutableStateOf(Mong())
    var undomong by mutableStateOf(MongCode.CH000)
    var stateCode by mutableStateOf(MongStateCode.CD000)
    var poopCount by mutableStateOf(0)
    var isHappy by mutableStateOf(false)

    var retry by mutableStateOf(false)

    // socket
    lateinit var managementSocketService: ManagementSocketService

    private val memberRepository: MemberRepository = MemberRepository()
    private val informationRepository: InformationRepository = InformationRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()

    // 메인화면 진입시 초기화
    fun init() {
        viewModelScope.launch(Dispatchers.Main) {
            findMong()
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
                            mong = Mong(
                                managementRealTimeResDto.mongId,
                                managementRealTimeResDto.name,
                                MongCode.valueOf(managementRealTimeResDto.mongCode)
                            )
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
                        }
                        "300" -> {
                            webSocket.send("connect")
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
    }

    // 포인트
    private fun findPayPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    point = data.point
                    findPayPointLoadingState = true
                }
        }
    }

    //진화
    fun evolution(){
        viewModelScope.launch(Dispatchers.IO) {
            undomong = mong.mongCode
            managementRepository.evolution()
                .catch {
                    it.printStackTrace()
                }
                .collect {
                    evolutionisClick = true
                    delay(2000)
                    evolutionisClick = false
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
                .collect{ data->
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
                        delay(2000)
                        isHappy = false
                    }
                }
        }
    }
}

class AppViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AppViewModel::class.java)){
            return AppViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}