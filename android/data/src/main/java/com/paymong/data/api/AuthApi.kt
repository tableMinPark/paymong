package com.paymong.data.api

import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.LoginResDto
import com.paymong.data.model.response.ReissueResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body loginReqDto: LoginReqDto) : Response<LoginResDto>

    @POST("/auth/login/watch")
    suspend fun watchLogin(@Body loginReqDto: LoginReqDto) : Response<LoginResDto>

    @POST("/auth/reissue")
    suspend fun reissue() : Response<ReissueResDto>
}