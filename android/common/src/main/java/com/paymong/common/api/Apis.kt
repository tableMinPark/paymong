package com.paymong.common.api

import com.paymong.common.dto.request.Register
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Apis {
    @POST("auth/register")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun register(@Body params: String) : Call<Register>

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

}
