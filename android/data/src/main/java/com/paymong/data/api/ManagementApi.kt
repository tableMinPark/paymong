package com.paymong.data.api

import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.response.AddMongResDto
import com.paymong.data.model.response.FindMongInfoResDto
import com.paymong.data.model.response.FindMongResDto
import com.paymong.data.model.response.FindMongStatsResDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ManagementApi {
    @POST("/management")
    suspend fun addMong(@Body addMongReqDto: AddMongReqDto) : Response<AddMongResDto>

    @PUT("/management/stroke")
    suspend fun stroke() : Response<Boolean>

    @PUT("/management/sleep/toggle")
    suspend fun sleep() : Response<Boolean>
    @PUT("/management/poop")
    suspend fun poop() : Response<Boolean>
}