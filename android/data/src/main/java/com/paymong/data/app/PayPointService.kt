package com.paymong.data.app

import com.paymong.common.dto.request.AddPayPointReqDto
import com.paymong.common.dto.response.AddPayPointResDto
import com.paymong.common.dto.response.FindPayPointResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PayPointService {

    @GET("/auth/test")
    fun test(
        @Header("Authorization") accessToken: String
    ): Call<String>

    @GET("paypoint")
    fun findPayPoint(
        @Header("Authorization") accessToken: String
    ): Call<FindPayPointResDto>

    @POST("paypoint")
    fun addPayPoint(
        @Header("Authorization") accessToken: String,
        @Body addPayPointReqDto: AddPayPointReqDto
    ): Call<AddPayPointResDto>
}