package com.paymong.domain.watch.battle

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.domain.watch.socket.SocketService
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.MessageType
import com.paymong.data.model.response.BattleErrorResDto
import com.paymong.data.model.response.BattleMessageResDto
import com.paymong.data.repository.InformationRepository
import com.paymong.domain.entity.BattleActive
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import okhttp3.WebSocket
import okhttp3.WebSocketListener

@SuppressLint("MissingPermission")
class BattleViewModel (
    private val application: Application
): AndroidViewModel(application)  {
    companion object {
        private const val FIND_DELAY = 2000L
        private const val ACTIVE_DELAY = 2000L
        private const val SELECT_BEFORE_DELAY = 2000L
        private const val SELECT_DELAY = 100L
        private const val SELECT_INTERVAL = 0.01
        private const val END_DELAY = 10000L
    }

    private var mongId by mutableStateOf(0L)
    var matchingState by mutableStateOf(MatchingCode.FINDING)
    var battleActive: BattleActive by mutableStateOf(BattleActive())


    var mongCode by mutableStateOf(CharacterCode.CH444)
    private val informationRepository: InformationRepository = InformationRepository()

    // socket
    private lateinit var socketJob : Job
    private lateinit var socketService: SocketService
    // gps
    private lateinit var gpsJob : Job
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest

    // Battle - 찾기
    var mongCodeA: String by mutableStateOf("")
    var mongCodeB: String by mutableStateOf("")
    
    // Battle - 진행
    var battleSelectTime by mutableStateOf(0.0)
    var selectState by mutableStateOf(MessageType.LEFT)

    // Battle - 끝
    var win by mutableStateOf(false)

    init {
        findMong()
    }
    fun setMongId(mongId: Long) {
        this.mongId = mongId
    }
    fun select(select : MessageType) {
        selectState = select
        matchingState = MatchingCode.SELECT_AFTER
    }
    private fun findMong() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMong()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mongCode = CharacterCode.valueOf(data.mongCode)
                }
        }
    }
    private fun findCharacterId(battleRoomId: String) {
        Log.d("battle", battleRoomId)

        // api 호출

        // characterCodeB에 소켓 에서 내려온 상대방 Code 넣기

        if (battleActive.order == "A") {
            mongCodeA = mongCode.code
//            characterCodeA = viewModel.mong.mongCode.code

            mongCodeB = "CH100"
        }
        else {
            mongCodeA = "CH100"
            mongCodeB = mongCode.code
        }
    }
    override fun onCleared() {
        super.onCleared()
        try {
            socketService.disConnect(mongId)
            gpsJob.cancel()
            socketJob.cancel()
        } catch (e: Exception) {  }
    }

    // -------------------------------------------------------------------------------------------------
    // socket
    private val listener: WebSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val battleMessageResDto = Gson().fromJson(text, BattleMessageResDto::class.java)

                if (battleMessageResDto.totalTurn == 0) {
                    val battleErrorResDto = Gson().fromJson(text, BattleErrorResDto::class.java)
                    matchingState = MatchingCode.NOT_FOUND
                } else {
                    // 탈주
                    battleActive = BattleActive(
                        battleMessageResDto.battleRoomId,
                        battleMessageResDto.nowTurn,
                        battleMessageResDto.totalTurn,
                        battleMessageResDto.nextAttacker,
                        battleMessageResDto.order,
                        battleMessageResDto.damageA,
                        battleMessageResDto.damageB,
                        battleMessageResDto.healthA,
                        battleMessageResDto.healthB
                    )
                    when (battleActive.nowTurn) {
                        0 -> {
                            // 시작
                            findCharacterId(battleActive.battleRoomId)
                            matchingState = MatchingCode.FOUND
                        }
                        -1 -> {
                            // 게임 끝
                            socketService.disConnect(mongId)
                            matchingState = MatchingCode.END
                        }
                        else -> {
                            // 다음 턴
                            matchingState = MatchingCode.ACTIVE_RESULT
                        }
                    }
                }
            } catch (e: Exception) {  }
        }
    }
    // gps
    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onLocationResult(locationResult: LocationResult) {
            // 위치 한번 받고 업데이트 요청 종료
            val latitude = locationResult.lastLocation.latitude
            val longitude = locationResult.lastLocation.longitude
//            val latitude = 35.0963554
//            val longitude = 128.8539052

            mFusedLocationProviderClient.removeLocationUpdates(this)

            socketService = SocketService()
            socketService.init(listener)

            socketJob = viewModelScope.launch {
                try {
                    socketService.connect(mongId, latitude, longitude)

                } catch (e: NullPointerException) {
                    Log.e("battle-matching", "서버가 유효하지 않음")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    // -------------------------------------------------------------------------------------------------

    // 배틀 대기열 등록 함수
    fun battleWait() {
        viewModelScope.launch {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
            mLocationRequest = LocationRequest.create().apply { priority = LocationRequest.PRIORITY_HIGH_ACCURACY }

            if (::mFusedLocationProviderClient.isInitialized) {
                mFusedLocationProviderClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    Looper.myLooper()!!
                )
            }
        }
    }

    // 배틀 찾았을 때 호출 할 함수
    fun battleFind() {
        viewModelScope.launch {
            delay(FIND_DELAY)
            matchingState = MatchingCode.ACTIVE
        }
    }

    // 배틀 찾기 실패했을 때 호출 할 함수
    fun battleFindFail() {
        viewModelScope.launch {
            matchingState = MatchingCode.FINDING
            try {
                socketService.disConnect(mongId)
                gpsJob.cancel()
                socketJob.cancel()
            } catch (e: Exception) {  }
        }
    }

    // 배틀 시작할 때 호출 할 함수
    fun battleActive() {
        viewModelScope.launch {
            delay(ACTIVE_DELAY)
            matchingState = MatchingCode.SELECT_BEFORE
        }
    }

    // 배틀 선택 전 호출 할 함수 (선택으로 넘어가기 위한 함수)
    fun battleSelectBefore() {
        viewModelScope.launch {
            delay(SELECT_BEFORE_DELAY)
            matchingState = MatchingCode.SELECT
        }
    }
    
    // 배틀 선택 시 호출 할 함수
    fun battleSelect() {
        viewModelScope.launch {
            selectState = MessageType.LEFT
            battleSelectTime = 0.0
            do {
                delay(SELECT_DELAY)
                battleSelectTime += SELECT_INTERVAL

                if (matchingState == MatchingCode.SELECT_AFTER)
                    break
            } while(battleSelectTime <= 1.0)

            socketService.select(selectState, mongId, battleActive.battleRoomId, battleActive.order)
            matchingState = MatchingCode.SELECT_AFTER
        }
    }

    // 배틀 끝난 후 호출 할 함수
    fun battleEnd() {
        viewModelScope.launch {
            if (battleActive.order == "A" &&
                battleActive.damageA > battleActive.damageB) {
                win = true
            }
            else if(battleActive.order == "B" &&
                    battleActive.damageB > battleActive.damageA){
                win = true
            }
            delay(END_DELAY)
            matchingState = MatchingCode.FINDING
        }
    }
}


