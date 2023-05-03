package com.paymong.domain.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.domain.entity.MongStats
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ConditionViewModel : ViewModel() {

    private lateinit var load : Job
    var mongStats by mutableStateOf(MongStats())

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            findMongCondition()
        }
    }

    fun findMongCondition() {
        val name = "별별이"
        val health = 0.35f
        val satiety = 0.62f
        val strength = 0.80f
        val sleep = 0.23f
        mongStats = MongStats(name, health, satiety, strength, sleep)
    }

    override fun onCleared() {
        super.onCleared()
    }
}