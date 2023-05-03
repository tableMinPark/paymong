package com.paymong.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.paymong.data.api.Api
import com.paymong.data.api.LoginApi
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.LoginResDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRepository(
    private val api: LoginApi = Api.getInstance().create(LoginApi::class.java)
) {
    fun login(loginReqDto: LoginReqDto) : LoginResDto {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.login(loginReqDto)
            if (response.isSuccessful) {
                Log.d("response", response.body().toString())
            } else
                throw RuntimeException()
        }
        return LoginResDto()
    }
}