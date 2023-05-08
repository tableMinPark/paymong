package com.paymong.data.repository

import com.paymong.data.api.Api
import com.paymong.data.api.ManagementApi
import com.paymong.data.api.MemberApi
import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MemberRepository(
    private val api: MemberApi = Api.getInstance().create(MemberApi::class.java)
) {
    fun findMember(): Flow<FindMemberResDto> = flow {
        val response = api.findMember()
        if (response.code() == 200) {
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }

    fun pointList(): Flow<List<PointInfoResDto>> = flow {
        val response = api.pointList()
        if(response.code() == 200){
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }

    fun findThings(): Flow<List<ThingsResDto>> = flow {
        val response = api.findThings()
        if(response.code() == 200){
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }

    fun addFindThings(): Flow<List<AddThingsResDto>> = flow {
        val response = api.addFindThings()
        if(response.code() == 200){
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }
}