package com.paymong.domain.watch.socket

import com.google.gson.*
import com.paymong.data.repository.DataApplicationRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class ManagementSocketService {
    object OkHttpClientSingleton {
        val instance: OkHttpClient = OkHttpClient.Builder()
//            .pingInterval(15, TimeUnit.SECONDS)
            .build()
    }

    private val url = "ws://dev.paymong.com:8080/management/ws"
    private lateinit var socket: OkHttpClient
    private lateinit var webSocket: WebSocket
    private lateinit var dataApplicationRepository: DataApplicationRepository

    class GsonDateFormatAdapter : JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {
        @Synchronized
        override fun serialize(localDateTime: LocalDateTime?, type: Type?, jsonSerializationContext: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(localDateTime))
        }
        @Synchronized
        override fun deserialize(jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext?): LocalDateTime {
            return LocalDateTime.parse(jsonElement.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }

    fun getGsonInstance() : Gson {
        return GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, GsonDateFormatAdapter()).create()
    }

    fun init(listener: WebSocketListener){
        dataApplicationRepository = DataApplicationRepository()
        val accessToken = dataApplicationRepository.getValue("accessToken")

        if (accessToken != "") {
            socket = OkHttpClientSingleton.instance
            val request: Request = Request.Builder().url(url)
                .header("Authorization", String.format("Bearer %s", accessToken)).build()
            webSocket = socket.newWebSocket(request, listener)
        }
    }

    fun disConnect() {
        webSocket.close(1000, "연결 종료")
    }
}