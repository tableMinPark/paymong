package com.paymong.data.app.api

import com.paymong.data.dto.request.LoginReq
import com.paymong.data.dto.response.LoginRes
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Apis {
    companion object {
        private const val BASE_URL = "http://dev.paymong.com:8080"
        fun create(): Apis {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Apis::class.java)
        }
    }

    @POST("/auth/login")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun register(@Body params: LoginReq) : Call<LoginRes>
}
