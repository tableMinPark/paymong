package com.paymong.data.api

import com.paymong.data.model.response.FindMongInfoResDto
import com.paymong.data.model.response.FindMongResDto
import com.paymong.data.model.response.FindMongStatsResDto
import retrofit2.Response
import retrofit2.http.GET

interface InformationApi {
    @GET("/information/mong")
    suspend fun findMong() : Response<FindMongResDto>

    @GET("/information/mong/info")
    suspend fun findMongInfo() : Response<FindMongInfoResDto>

    @GET("/information/mong/status")
    suspend fun findMongStats() : Response<FindMongStatsResDto>
}