package com.paymong.data.api

import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.request.ReissueReqDto
import com.paymong.data.model.response.LoginResDto
import com.paymong.data.model.response.ReissueResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    fun login(@Body loginReqDto: LoginReqDto) : Call<LoginResDto>
    @POST("/auth/reissue")
    fun reissue(@Body reissueReqDto: ReissueReqDto) : Call<ReissueResDto>
}