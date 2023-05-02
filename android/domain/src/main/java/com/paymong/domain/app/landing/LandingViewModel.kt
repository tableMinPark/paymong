package com.paymong.domain.app.landing

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LandingViewModel(application : Application) : AndroidViewModel(application) {
    var id by mutableStateOf("")
    private lateinit var load : Job



    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("loginId",Context.MODE_PRIVATE)
            id = sharedPref.getString("loginId","").toString()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}