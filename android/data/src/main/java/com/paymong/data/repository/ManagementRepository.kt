package com.paymong.data.repository

import com.paymong.data.api.Api
import com.paymong.data.api.ManagementApi
import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.response.AddMongResDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ManagementRepository(
    private val api: ManagementApi = Api.getInstance().create(ManagementApi::class.java)
) {
    fun addMong(addMongReqDto: AddMongReqDto): Flow<AddMongResDto> = flow {
        val response = api.addMong(addMongReqDto)
        if (response.code() == 200) {
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }
}