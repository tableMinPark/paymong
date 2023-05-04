package com.paymong.data.api

import com.paymong.data.model.request.CreateReqDto
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.CreateResDto
import com.paymong.data.model.response.LoginResDto
import com.paymong.data.model.response.MapResDto
import com.paymong.data.model.response.MongResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CollectApi {
    @GET("/collect/map")
    fun map() : Call<List<MapResDto>>
    @GET("/collect/mong")
    fun mong() : Call<MongResDto>
}