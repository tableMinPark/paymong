package com.paymong.domain.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.paymong.data.model.request.LoginReqDto
import com.paymong.data.repository.LoginRepository

class LoginViewModel : ViewModel() {

    var isAuthenticated by mutableStateOf(false)
    private var loginRepository: LoginRepository = LoginRepository()

    init {

    }

    override fun onCleared() {
        super.onCleared()
    }

    fun login(playerId: String) : Boolean {
        return loginRepository.login(LoginReqDto(playerId))
    }
}