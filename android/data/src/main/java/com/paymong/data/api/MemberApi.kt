package com.paymong.data.api

import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.request.AddPayReqDto
import com.paymong.data.model.request.AddThingsReqDto
import com.paymong.data.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface MemberApi {
    @POST("/member/paypoint")
    suspend fun addPay(@Body addPayReqDto: AddPayReqDto) : Response<Boolean>

    @GET("/member/info")
    suspend fun findMember() : Response<FindMemberResDto>

    @GET("/member/paypoint/list")
    suspend fun pointList() : Response<List<PointInfoResDto>>

    @GET("/member/things")
    suspend fun findThings() : Response<List<ThingsResDto>>

    @POST("/member/things")
    suspend fun addThings(@Body addThings: AddThingsReqDto) : Response<Boolean>

    @HTTP(method="DELETE", path = "/member/things", hasBody = true)
    suspend fun deleteThings(@Body thingsId: Long) : Response<Boolean>

    @GET("/member/things/addable")
    suspend fun addFindThings() : Response<List<AddThingsResDto>>
}