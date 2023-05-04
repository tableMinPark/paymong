package com.paymong.data.api

import com.paymong.data.model.request.CreateReqDto
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.CreateResDto
import com.paymong.data.model.response.LoginResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ManagementApi {
    @POST("/management")
    fun create(@Body createReqDto: CreateReqDto) : Call<CreateResDto>
}