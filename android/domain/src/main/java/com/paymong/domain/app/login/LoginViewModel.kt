package com.paymong.domain.app.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.LoginCode
import com.paymong.data.app.repository.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(application : Application) : AndroidViewModel(application) {
    var id by mutableStateOf("")
    var isClicked by mutableStateOf(LoginCode.BEFORE_CLICK)
    private lateinit var load : Job
    private val loginRepository: LoginRepository = LoginRepository()

    init {

        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            if(isClicked == LoginCode.AFTER_CLICK){
                Log.d("click", id)
                loginRepository.registerLogin(id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun login(){
        Log.d("login", id)
        loginRepository.registerLogin(id)
    }
}