package com.paymong.data.api

import com.paymong.data.model.response.MapResDto
import com.paymong.data.model.response.MongResDto
import retrofit2.Call
import retrofit2.http.GET

interface CollectApi {
    @GET("/collect/map")
    fun map() : Call<List<MapResDto>>
    @GET("/collect/mong")
    fun mong() : Call<MongResDto>
}