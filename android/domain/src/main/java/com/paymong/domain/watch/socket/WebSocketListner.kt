package com.paymong.domain.watch.socket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paymong.data.watch.dto.response.BattleMessageResDto
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketListener : WebSocketListener() {

    private val _battleMessageResDto = MutableLiveData<String>()
    val battleMessageResDto: LiveData<String> get() = _battleMessageResDto

    override fun onOpen(webSocket: WebSocket, response: Response?) {
        Log.d("Socket","시작!")

    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        Log.d("Socket","수신 : $text")
        _battleMessageResDto.value = text
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("Socket","끝!")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        webSocket.cancel()
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
        Log.d("Socket","Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}