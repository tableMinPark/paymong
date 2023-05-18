package com.paymong.data.repository

import android.util.Log
import com.paymong.data.DataApplication
import com.paymong.data.api.Api
import com.paymong.data.api.AuthApi
import com.paymong.data.model.request.LoginReqDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AuthRepository (
    private val api: AuthApi = Api.getInstance().create(AuthApi::class.java)
) {

    @Throws(IOException::class)
    fun login(loginReqDto: LoginReqDto): Flow<Boolean> = flow {
        val response = api.login(loginReqDto)
        if (response.code() == 200) {
            response.body()?.let {
                DataApplication.prefs.setString("accessToken", response.body()!!.accessToken)
                DataApplication.prefs.setString("refreshToken", response.body()!!.refreshToken)
                emit(true)
            }
        } else {
            emit(false)
        }
    }

    fun watchLogin(loginReqDto: LoginReqDto): Flow<Boolean> = flow {
        val response = api.watchLogin(loginReqDto)
        if (response.code() == 200) {
            response.body()?.let {
                DataApplication.prefs.setString("accessToken", response.body()!!.accessToken)
                DataApplication.prefs.setString("refreshToken", response.body()!!.refreshToken)
                emit(true)
            }
        } else {
            emit(false)
        }
    }

    fun reissue(): Flow<Boolean> = flow {
        val response = api.reissue()

        if (response.code() == 200) {
            DataApplication.prefs.setString("accessToken", response.body()!!.accessToken)
            emit(true)
        } else {
            emit(false)
        }
    }
}