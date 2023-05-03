package com.paymong.data.api

import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.LoginResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/auth/login")
    suspend fun login(@Body loginReqDto: LoginReqDto) : Response<LoginResDto>
}