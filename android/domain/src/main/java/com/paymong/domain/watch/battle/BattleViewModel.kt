package com.paymong.domain.watch.battle

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
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
import com.paymong.common.code.MatchingCode
import com.paymong.common.code.MessageType
import com.paymong.common.dto.response.BattleErrorResDto
import com.paymong.common.dto.response.BattleMessageResDto
import com.paymong.common.entity.BattleActiveEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.time.Duration
import java.time.LocalDateTime

@SuppressLint("MissingPermission")
class BattleViewModel (application: Application): AndroidViewModel(application)  {

    private var context : Context

    var matchingState by mutableStateOf(MatchingCode.FINDING)
    var battleActiveEntity: BattleActiveEntity by mutableStateOf(BattleActiveEntity())

    private var characterId = 0L

    init {
        Log.d("battle", "init - Call")
        context = application
        Log.d("viewmodel", "viewModel 생성")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("battle", "onCleared - Call")
        Log.d("battle", "자원 할당 해제")
        try {
            socketService.disConnect(characterId)
            gpsJob.cancel()
            socketJob.cancel()
        } catch (e: Exception) {
            Log.e("battle", "자원 할당 해제")
        }
    }

    fun select(select : MessageType) {
        Log.d("battle", "select - Call")
        selectState = select
        matchingState = MatchingCode.SELECT_AFTER
    }

    // -------------------------------------------------------------------------------------------------
    private val WAIT_DELAY = 5000L
    private val WAIT_MAX_TIME = 30L
    // socket
    private lateinit var socketJob : Job
    private lateinit var socketService: SocketService
    private val listener: WebSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket?, text: String) {
            try {
                Log.e("battle", text)

                val battleMessageResDto = Gson().fromJson(text, BattleMessageResDto::class.java)

                if (battleMessageResDto.totalTurn == 0) {
                    val battleErrorResDto = Gson().fromJson(text, BattleErrorResDto::class.java)
                    Log.e("battle-matching", battleErrorResDto.toString())
                    matchingState = MatchingCode.NOT_FOUND
                } else {
                    Log.e("battle-matching", battleActiveEntity.toString())
                    // 탈주
                    battleActiveEntity = BattleActiveEntity(
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

                    if (battleActiveEntity.nowTurn == 0) {
                        // 시작
                        findCharacterId(battleActiveEntity.battleRoomId)
                        matchingState = MatchingCode.FOUND
                    } else if (battleActiveEntity.nowTurn == -1) {
                        // 게임 끝
                        socketService.disConnect(characterId)
                        matchingState = MatchingCode.END
                    } else {
                        // 다음 턴
                        matchingState = MatchingCode.ACTIVE_RESULT
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // gps
    private lateinit var gpsJob : Job
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest
    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onLocationResult(locationResult: LocationResult) {
            // 위치 한번 받고 업데이트 요청 종료
            val latitude = locationResult.lastLocation.latitude
            val longitude = locationResult.lastLocation.longitude
            mFusedLocationProviderClient.removeLocationUpdates(this)

            Log.d("battle-matching", "소켓 연결 시작")
            socketService = SocketService()
            socketService.init(listener)

            Log.d("battle-matching", "소켓 연결 성공")
            socketJob = viewModelScope.launch {
                try {
                    val startTime: LocalDateTime = LocalDateTime.now()
                    var duringTime: Long

                    // 매칭
                    Log.d("battle-matching", "매칭 - 위도 : $latitude / 경도 : $longitude")
                    socketService.connect(characterId, latitude, longitude)

                } catch (e: NullPointerException) {
                    Log.e("battle-matching", "서버가 유효하지 않음")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun battleWait() {
        Log.d("battle", "battleWait - Call")
        // 캐릭터 ID 리드
        val range = (1..15)
        characterId = range.random().toLong()

        gpsJob = viewModelScope.launch {
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            mLocationRequest = LocationRequest.create().apply { priority = LocationRequest.PRIORITY_HIGH_ACCURACY }

            if (::mFusedLocationProviderClient.isInitialized) {
                mFusedLocationProviderClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    Looper.myLooper()
                )
            }
        }
    }

    // -------------------------------------------------------------------------------------------------
    private val FIND_DELAY = 2000L
    var characterCodeA: String by mutableStateOf("")
    var characterCodeB: String by mutableStateOf("")
    fun battleFind() {
        Log.d("battle", "battleFind - Call")
        viewModelScope.launch {
            delay(FIND_DELAY)
            matchingState = MatchingCode.ACTIVE
        }
    }

    // -------------------------------------------------------------------------------------------------
    fun battleFindFail() {
        Log.d("battle", "battleFindFail - Call")
        viewModelScope.launch {
            matchingState = MatchingCode.FINDING
            try {
                socketService.disConnect(characterId)
                gpsJob.cancel()
                socketJob.cancel()
            } catch (e: Exception) {
                Log.d("battle", "자원 할당 해제")
            }
        }
    }

    // -------------------------------------------------------------------------------------------------
    private val ACTIVE_DELAY = 2000L
    var TOTAL_TURN = 10
    fun battleActive() {
        Log.d("battle", "battleActive - Call")
        viewModelScope.launch {
            delay(ACTIVE_DELAY)
            matchingState = MatchingCode.SELECT_BEFORE
        }
    }

    // -------------------------------------------------------------------------------------------------
    private val SELECT_BEFORE_DELAY = 2000L
    fun battleSelectBefore() {
        Log.d("battle", "battleSelectBefore - Call")
        viewModelScope.launch {
            delay(SELECT_BEFORE_DELAY)
            matchingState = MatchingCode.SELECT
        }
    }

    // -------------------------------------------------------------------------------------------------
    private val SELECT_DELAY = 100L
    private val SELECT_INTERVAL = 0.01
    var battleSelectTime by mutableStateOf(0.0)
    var selectState by mutableStateOf(MessageType.LEFT)
    fun battleSelect() {
        Log.d("battle", "battleSelect - Call")
        viewModelScope.launch {
            selectState = MessageType.LEFT
            battleSelectTime = 0.0
            do {
                delay(SELECT_DELAY)
                battleSelectTime += SELECT_INTERVAL

                if (matchingState == MatchingCode.SELECT_AFTER) {
                    break
                }

            } while(battleSelectTime <= 1.0)

            matchingState = MatchingCode.SELECT_AFTER
            socketService.select(selectState, characterId, battleActiveEntity.battleRoomId, battleActiveEntity.order)
        }
    }

    // -------------------------------------------------------------------------------------------------
    private val END_DELAY = 10000L
    var win by mutableStateOf(false)
    var characterCode by mutableStateOf("")
    fun battleEnd() {
        Log.d("battle", "battleEnd - Call")
        viewModelScope.launch {
            if (battleActiveEntity.order == "A") {
                characterCode = characterCodeA
                if (battleActiveEntity.damageA > battleActiveEntity.damageB) {
                    win = true
                }
            } else {
                characterCode = characterCodeB
                if (battleActiveEntity.damageB > battleActiveEntity.damageA) {
                    win = true
                }
            }
            delay(END_DELAY)
            matchingState = MatchingCode.FINDING
        }
    }

    private fun findCharacterId(battleRoomId: String) {
        Log.d("viewmodel", "findCharacterId()")
        val sp : List<String> = battleRoomId.split("-")
        val characterIdA = sp[0]
        val characterIdB = sp[1]
        
        // api 호출

        characterCodeA = "CH102"
        characterCodeB = "CH100"
    }
}