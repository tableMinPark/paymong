package com.paymong.data.repository

import android.util.Log
import com.paymong.data.DataApplication
import com.paymong.data.api.Api
import com.paymong.data.api.AuthApi
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.request.ReissueReqDto
import com.paymong.data.model.response.LoginResDto
import com.paymong.data.model.response.ReissueResDto
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

        // test
        if (!isSuccess) {
            isSuccess = true
            val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IjEyMzQ1Njc4OTEwIiwiaWF0IjoxNjgzMTc2NTkyLCJleHAiOjE2ODU3Njg1OTJ9.cMIetIqNcOqKiZtm-qo_aO__nr9KuDggSVgEoh5hQFE"
            val refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IjEyMzQ1Njc4OTEwIiwiaWF0IjoxNjgzMTc2NTkyLCJleHAiOjE2OTA5NTI1OTJ9.KgvL32jXet04NLhdHjkosw3yCmY5msPoGCUhe9NvhY0"
            DataApplication.prefs.setString("accessToken", accessToken)
            DataApplication.prefs.setString("refreshToken", refreshToken)
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            api.login(loginReqDto).enqueue(object : Callback<LoginResDto> {
//                override fun onResponse(call: Call<LoginResDto>, response: Response<LoginResDto>) {
//                    if (response.isSuccessful) {
//                        val body = response.body()
//
//                        if (body != null) {
//                            isSuccess = true
//                            DataApplication.prefs.setString("accessToken", body.accessToken)
//                            DataApplication.prefs.setString("refreshToken", body.refreshToken)
//                            Log.d("login", body.accessToken)
//                            Log.d("login", body.refreshToken)
//                        }
//                    }
//                }
//                override fun onFailure(call: Call<LoginResDto>, t: Throwable) {
//                    call.cancel()
//                }
//            })
//        }
        return isSuccess
    }

    fun reissue() : Boolean {
        var isSuccess = false

        // test
        val refreshToken = DataApplication.prefs.getString("refreshToken", "")
        if ("" != refreshToken) {
            isSuccess = true
            val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IjEyMzQ1Njc4OTEwIiwiaWF0IjoxNjgzMTc2NTkyLCJleHAiOjE2ODU3Njg1OTJ9.cMIetIqNcOqKiZtm-qo_aO__nr9KuDggSVgEoh5hQFE"
            DataApplication.prefs.setString("accessToken", accessToken)
        }

//        val refreshToken = DataApplication.prefs.getString("refreshToken", "")
//        if ("" != refreshToken) {
//            CoroutineScope(Dispatchers.IO).launch {
//                api.reissue(ReissueReqDto(refreshToken)).enqueue(object : Callback<ReissueResDto> {
//                    override fun onResponse(call: Call<ReissueResDto>, response: Response<ReissueResDto>) {
//                        if (response.isSuccessful) {
//                            val body = response.body()
//                            if (body != null) {
//                                isSuccess = true
//                                DataApplication.prefs.setString("accessToken", body.accessToken)
//                            }
//                        }
//                    }
//                    override fun onFailure(call: Call<ReissueResDto>, t: Throwable) {
//                        call.cancel()
//                    }
//                })
//            }
//        }
        return isSuccess
    }
}