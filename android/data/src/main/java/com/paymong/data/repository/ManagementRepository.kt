package com.paymong.data.repository

import android.util.Log
import com.paymong.data.api.Api
import com.paymong.data.api.ManagementApi
import com.paymong.data.model.request.AddMongReqDto
import com.paymong.data.model.response.AddMongResDto
import com.paymong.data.model.response.EvolutionResDto
import com.paymong.data.model.response.FoodResDto
import com.paymong.data.model.response.ManagementResDto
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

    fun getFoodList(foodCategory: String): Flow<List<FoodResDto>> = flow {
        val response = api.getFood(foodCategory)
        if(response.code() == 200){
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }

    fun stroke() : Flow<ManagementResDto> = flow {
        val response = api.stroke()
        if(response.code() == 200){
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }

    fun sleep() : Flow<Boolean> = flow {
        val response = api.sleep()
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
            }
        }
    }
    fun poop() : Flow<Boolean> = flow {
        val response = api.poop()
        if(response.code() == 200){
            response.body()?.let{
                emit(true)
            }
        }
    }

    fun eatFood(foodCode: String) : Flow<Boolean> = flow {
        val response = api.eatFood(foodCode)
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
            }
        }
    }

    fun eatSnack(snackCode: String) : Flow<Boolean> = flow {
        val response = api.eatSnack(snackCode)
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
            }
        }
    }

    fun training(trainingCount: Int): Flow<Boolean> = flow {
        val response = api.training(trainingCount)
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
            }
        }
    }

    fun walking(walkingCount: Int): Flow<Boolean> = flow {
        val response = api.walking(walkingCount)
        if(response.code() == 200){
            response.body()?.let {
                emit(true)
            }
        }
    }

    fun evolution(): Flow<EvolutionResDto> = flow {
        val response = api.evolution()
        if(response.code() == 200){
            response.body()?.let {
                emit(response.body()!!)
            }
        }
    }
}