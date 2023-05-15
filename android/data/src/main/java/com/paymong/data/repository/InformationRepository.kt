package com.paymong.data.repository

import com.paymong.data.api.Api
import com.paymong.data.api.InformationApi
import com.paymong.data.model.response.FindMongInfoResDto
import com.paymong.data.model.response.FindMongResDto
import com.paymong.data.model.response.FindMongStatsResDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InformationRepository (
    private val api: InformationApi = Api.getInstance().create(InformationApi::class.java)
) {
    fun findMong(): Flow<FindMongResDto> = flow {
        val response = api.findMong()
        if (response.code() == 200) {
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }

    fun findMongInfo(): Flow<FindMongInfoResDto> = flow {
        val response = api.findMongInfo()
        if (response.code() == 200) {
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }

    fun findMongStats(): Flow<FindMongStatsResDto> = flow {
        val response = api.findMongStats()
        if (response.code() == 200) {
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }
}