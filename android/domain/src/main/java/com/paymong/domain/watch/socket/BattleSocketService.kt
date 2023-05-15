package com.paymong.domain.watch.socket

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.paymong.common.code.MessageType
import com.paymong.data.model.request.BattleConnectReqDto
import com.paymong.data.model.request.BattleMessageReqDto
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class BattleSocketService {
    object OkHttpClientSingleton {
        val instance: OkHttpClient = OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
    }

    private val url = "ws://dev.paymong.com:8080/battle"
    private lateinit var socket: OkHttpClient
    private lateinit var webSocket: WebSocket

    @RequiresApi(Build.VERSION_CODES.O)
    fun init(listener: WebSocketListener){
        socket = OkHttpClientSingleton.instance
        val request: Request = Request.Builder().url(url).build()
        webSocket = socket.newWebSocket(request, listener)
    }

    fun connect(mongId: Long, mongCode: String, latitude: Double, longitude: Double) {
        val battleMessageResDto = BattleConnectReqDto(MessageType.CONNECT, mongId, mongCode, latitude, longitude)
        val json = Gson().toJson(battleMessageResDto)
        webSocket.send(json)
    }

    fun disConnect(mongId: Long, mongCode: String) {
        val battleMessageResDto = BattleConnectReqDto(MessageType.DISCONNECT, mongId, mongCode, 0.0, 0.0)
        val json = Gson().toJson(battleMessageResDto)
        webSocket.send(json)
        webSocket.close(1000, "연결 종료")
    }

    fun select(type: MessageType, characterId: Long, battleRoomId: String, order: String) {
        val battleMessageReqDto =
            BattleMessageReqDto(type, characterId, battleRoomId, order)
        val json = Gson().toJson(battleMessageReqDto)
        webSocket.send(json)
    }
}