package com.paymong.data.repository

import com.paymong.data.DataApplication
import com.paymong.data.api.Api
import com.paymong.data.api.AuthApi
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.LoginResDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(
    private val api: AuthApi = Api.getInstance().create(AuthApi::class.java)
) {
    fun login(loginReqDto: LoginReqDto) : Boolean {
        var isSuccess = false
        CoroutineScope(Dispatchers.IO).launch {
            api.login(loginReqDto).enqueue(object : Callback<LoginResDto> {
                override fun onResponse(call: Call<LoginResDto>, response: Response<LoginResDto>) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null) {
                            isSuccess = true
                            DataApplication.prefs.setString("accessToken", body.accessToken)
                            DataApplication.prefs.setString("refreshToken", body.refreshToken)
                        }
                    }
                }
                override fun onFailure(call: Call<LoginResDto>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        return isSuccess
    }
}