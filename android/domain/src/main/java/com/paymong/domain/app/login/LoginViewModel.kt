package com.paymong.domain.app.login

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.app.repository.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(application : Application) : AndroidViewModel(application) {
    var id by mutableStateOf("")
    var isClicked by mutableStateOf(false)
    private lateinit var load : Job
    private val loginRepository: LoginRepository = LoginRepository()

    init {

        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            isClicked = false
            val sharedPref = application.getSharedPreferences("loginId", Context.MODE_PRIVATE)
            id = sharedPref.getString("loginId","").toString()

            if(isClicked){

            }
            loginRepository.registerLogin(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}