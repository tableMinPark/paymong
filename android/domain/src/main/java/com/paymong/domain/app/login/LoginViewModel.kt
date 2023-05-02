package com.paymong.domain.app.login

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.app.api.Apis
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(application : Application) : AndroidViewModel(application) {
    var id by mutableStateOf("")
    private lateinit var load : Job
    val api = Apis.create()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("loginId", Context.MODE_PRIVATE)
            id = sharedPref.getString("loginId","").toString()

            if(id!=""){
                api.register(id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}