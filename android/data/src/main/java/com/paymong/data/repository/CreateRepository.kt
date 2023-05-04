package com.paymong.data.repository

import android.util.Log
import com.paymong.data.DataApplication
import com.paymong.data.api.Api
import com.paymong.data.api.AuthApi
import com.paymong.data.api.ManagementApi
import com.paymong.data.model.request.CreateReqDto
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.CreateResDto
import com.paymong.data.model.response.LoginResDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class CreateRepository(
    private val api: ManagementApi = Api.getInstance().create(ManagementApi::class.java)
) {
    fun create(createReqDto: CreateReqDto) : CreateResDto? {
        var body : CreateResDto? = CreateResDto("","",0, LocalDateTime.now())
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(".","들어가냐")
            api.create(createReqDto).enqueue(object : Callback<CreateResDto> {
                override fun onResponse(call: Call<CreateResDto>, response: Response<CreateResDto>) {
                    Log.d("resres", response.toString())
                    if (response.isSuccessful) {
                        body = response.body()
                        Log.d("createRes",response.body().toString())
                    }

                }
                override fun onFailure(call: Call<CreateResDto>, t: Throwable) {
                    Log.d("fail", t.toString())
                    call.cancel()
                }
            })
        }
        return body
    }
}