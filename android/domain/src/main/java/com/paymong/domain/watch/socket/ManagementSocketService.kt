package com.paymong.domain.watch.socket

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.paymong.common.code.MessageType
import com.paymong.data.model.request.BattleConnectReqDto
import com.paymong.data.model.request.BattleMessageReqDto
import com.paymong.data.repository.DataApplicationRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class ManagementSocketService {
    object OkHttpClientSingleton {
        val instance: OkHttpClient = OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
    }

    private val url = "ws://dev.paymong.com:8080/management/ws"
    private lateinit var socket: OkHttpClient
    private lateinit var webSocket: WebSocket
    private lateinit var dataApplicationRepository: DataApplicationRepository

    @RequiresApi(Build.VERSION_CODES.O)
    fun init(listener: WebSocketListener){
        dataApplicationRepository = DataApplicationRepository()
        val accessToken = dataApplicationRepository.getValue("accessToken")

        if (accessToken != "") {
            socket = OkHttpClientSingleton.instance
            val request: Request = Request.Builder().url(url).header("Authorization", String.format("Bearer %s", accessToken)).build()
            webSocket = socket.newWebSocket(request, listener)
        }
        Log.e("ManagementSocketService", "연결 성공")
    }
    fun disConnect() {
        Log.e("ManagementSocketService", "연결 종료")
        webSocket.close(1000, "연결 종료")
    }
}