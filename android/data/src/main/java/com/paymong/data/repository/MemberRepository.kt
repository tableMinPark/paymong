package com.paymong.data.repository

import android.util.Log
import com.paymong.data.api.Api
import com.paymong.data.api.MemberApi
import com.paymong.data.model.request.AddPayReqDto
import com.paymong.data.model.request.AddRoutineReqDto
import com.paymong.data.model.request.AddThingsReqDto
import com.paymong.data.model.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MemberRepository(
    private val api: MemberApi = Api.getInstance().create(MemberApi::class.java)
) {
    fun addPay(addPayReqDto: AddPayReqDto): Flow<Boolean> = flow {
        val response = api.addPay(addPayReqDto)
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
            }
        }
    }

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
    fun addThings(addThings: AddThingsReqDto): Flow<Boolean> = flow {
        val response = api.addThings(addThings)
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
            }
        }
    }
    fun deleteThings(thingsId: Long): Flow<Boolean> = flow {
        val response = api.deleteThings(thingsId)
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
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

    fun addRoutine(addRoutineReqDto: AddRoutineReqDto) : Flow<AddRoutineResDto> = flow {
        val response = api.addRoutine(addRoutineReqDto)
        if(response.code() == 200){
            response.body()?.let {
                emit(response.body()!!)
            }
        } else {
            Log.e("thing 루틴 response", "등록안된 루틴명")
        }
    }
}