package com.paymong.data.api

import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.LoginResDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    companion object {
        private const val BASE_URL = "http://dev.paymong.com:8080/"

        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
