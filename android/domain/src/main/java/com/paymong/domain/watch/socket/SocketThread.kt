package com.paymong.domain.watch.socket

import android.util.Log
import com.google.gson.Gson
import com.paymong.common.code.MessageType
import com.paymong.data.watch.dto.request.BattleConnectReqDto
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.net.UnknownHostException
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit


class SocketThread constructor(
    webSocketListener: WebSocketListener
): Thread() {
    object OkHttpClientSingleton {
        val instance: OkHttpClient = OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private var listener: WebSocketListener
    private lateinit var socket: OkHttpClient
    private lateinit var webSocket: WebSocket
    private val timeout: Long = 60
    private var running: Boolean = false
    private val url = "ws://k8e103.p.ssafy.io:11050"

    init {
        listener = webSocketListener
    }

    override fun run() {
        super.run()


        val startTime = LocalDateTime.now()
        var nowTime: Long

        try {
            running = true

            socket = OkHttpClientSingleton.instance
            val request: Request = Request.Builder().url(url).build()
            // 소켓 생성 성공
            Log.d("socket", "소켓 연결 성공")
            webSocket = socket.newWebSocket(request, listener)

            Log.d("socket", "연결 요청 성공")
            val battleMessageResDto = BattleConnectReqDto(MessageType.CONNECT, 1L, 35.089469, 128.853372)
            val json = Gson().toJson(battleMessageResDto)
            webSocket.send(json)

            while(running) {
                nowTime = Duration.between(startTime, LocalDateTime.now()).seconds

                if (nowTime >= timeout) {
                    Log.d("socket", "연결 요청 성공")
                    break
                }
            }
            webSocket.close(1000, "게임 끝")
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Log.d("socket", "UnknownHostException")
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Log.d("socket", "InterruptedException")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("socket", "Exception")
        }
    }
}