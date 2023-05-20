package com.paymong.data.socket

import android.content.res.Resources
import android.util.Log
import com.google.gson.*
import com.paymong.data.DataApplication
import com.paymong.data.repository.AuthRepository
import com.paymong.data.repository.DataApplicationRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.io.IOException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class ManagementSocketService {
    object OkHttpClientSingleton {
        val instance: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AccessTokenExpireInterceptor())
            .pingInterval(1, TimeUnit.SECONDS)
            .build()
    }

    private val url = "ws://dev.paymong.com:8080/management/ws"
    private lateinit var socket: OkHttpClient
    private lateinit var webSocket: WebSocket

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
        val accessToken = DataApplicationRepository().getValue("accessToken")
        socket = OkHttpClientSingleton.instance
        val request: Request = Request.Builder().url(url)
            .header("Authorization", String.format("Bearer %s", accessToken)).build()
        webSocket = socket.newWebSocket(request, listener)
    }

    private class AccessTokenExpireInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val response = chain.proceed(chain.request())

            // 토큰 만료
            if (response.code() == 403) {
                try {
                    AuthRepository().reissue()
                    // 토큰 재발급 성공 시 기존 요청 재전송
                    val accessToken = DataApplicationRepository().getValue("accessToken")
                    val token = "Bearer $accessToken"
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", token)
                        .build()
                    return chain.proceed(newRequest)
                }
                // refreshToken 만료
                catch (e: Resources.NotFoundException){
                    DataApplicationRepository().setValue("accessToken", "")
                    DataApplicationRepository().setValue("refreshToken", "")
                }
            }
            return response
        }
    }

    fun disConnect() {
        webSocket.close(1000, "연결 종료")
    }
}