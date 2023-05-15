package com.paymong.domain.watch.socket

import android.os.Build
import androidx.annotation.RequiresApi
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
    }
    fun disConnect() {
        webSocket.close(1000, "연결 종료")
    }
}