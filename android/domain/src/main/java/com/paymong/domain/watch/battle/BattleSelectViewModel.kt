package com.paymong.domain.watch.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class BattleSelectViewModel : ViewModel() {
    var isSelectEnd by mutableStateOf(false)
    var second by mutableStateOf(0)

    private lateinit var timer : Job
    private var nowTime : Long = 0
    private var interval : Long = 10
    private val maxTime : Long = 3000

    init {
        isSelectEnd = false
        second = 0
        nowTime = 0
        timerStart()
    }

    private fun timerStart(){
        if(::timer.isInitialized) timer.cancel()

        timer = viewModelScope.launch {
            while(nowTime < maxTime) {
                delay(interval)
                nowTime += interval

                val now = Instant.ofEpochMilli(nowTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
                second = now.second
            }
            // 시간 초과 종료
            isSelectEnd = true
        }
    }

    fun navigateToActive(navigate: () -> Unit){
        if(isSelectEnd){
            navigate()
        }
    }
}