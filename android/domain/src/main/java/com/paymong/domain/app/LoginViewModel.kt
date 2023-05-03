package com.paymong.domain.app

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.model.response.LoginResDto
import com.paymong.data.repository.LoginRepository

class LoginViewModel(application : Application) : AndroidViewModel(application) {

    var isAuthenticated by mutableStateOf(false)

    private var context : Context
    private var loginRepository: LoginRepository

    init {
        context = application
        loginRepository = LoginRepository()
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun login(playerId: String) {
        val loginResDto : LoginResDto = loginRepository.login( LoginReqDto(playerId) )
        val sharedPref = context.getSharedPreferences("playerInfo", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("accessToken", loginResDto.accessToken)
            putString("refreshToken", loginResDto.refreshToken)
            apply()
        }
    }
}