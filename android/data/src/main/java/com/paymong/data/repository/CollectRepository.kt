package com.paymong.data.repository

import com.paymong.data.api.Api
import com.paymong.data.api.CollectApi
import com.paymong.data.model.response.MapResDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CollectRepository (
    private val api: CollectApi = Api.getInstance().create(CollectApi::class.java)
) {
    fun map(): Flow<List<MapResDto>> = flow {
        val response = api.map()
        if (response.code() == 200) {
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }
}