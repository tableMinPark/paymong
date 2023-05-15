package com.paymong.data.api

import com.paymong.data.model.response.MapResDto
import com.paymong.data.model.response.MongResDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CollectApi {
    @GET("/collect/map")
    suspend fun map() : Response<List<MapResDto>>
    @GET("/collect/mong")
    suspend fun mong() : Response<List<MongResDto>>
}