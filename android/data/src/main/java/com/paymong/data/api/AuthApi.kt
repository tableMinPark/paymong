package com.paymong.data.api

import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.LoginResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    fun login(@Body loginReqDto: LoginReqDto) : Call<LoginResDto>
}