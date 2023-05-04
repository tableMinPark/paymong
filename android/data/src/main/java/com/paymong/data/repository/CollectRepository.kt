package com.paymong.data.repository

import com.paymong.data.api.Api
import com.paymong.data.api.CollectApi
import com.paymong.data.api.ManagementApi
import com.paymong.data.model.request.CreateReqDto
import com.paymong.data.model.response.CreateResDto
import com.paymong.data.model.response.MapResDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class CollectRepository (
    private val api: CollectApi = Api.getInstance().create(CollectApi::class.java)
) {
    fun map() : List<MapResDto>? {
        var body = listOf<MapResDto>()
        CoroutineScope(Dispatchers.IO).launch {
            api.map().enqueue(object : Callback<List<MapResDto>> {
                override fun onResponse(call: Call<List<MapResDto>>, response: Response<List<MapResDto>>) {
                    if (response.isSuccessful) {
                        body = response.body()!!
                    }
                }
                override fun onFailure(call: Call<List<MapResDto>>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        return body
    }
}