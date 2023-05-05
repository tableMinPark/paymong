package com.paymong.data.api

import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemberApi {
    @GET("/member/info")
    suspend fun findMember() : Response<FindMemberResDto>
}