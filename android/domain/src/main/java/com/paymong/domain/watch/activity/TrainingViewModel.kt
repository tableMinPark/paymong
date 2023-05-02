package com.paymong.domain.watch.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class TrainingViewModel : ViewModel() {
    var isTrainingEnd by mutableStateOf(false)
    var second by mutableStateOf(0)
    var nanoSecond by mutableStateOf(0)
    var count : Long = 0

    private lateinit var timer : Job
    private var nowTime : Long = 0
    private var interval : Long = 10
    private val maxTime : Long = 1000  // 10000

    init {
        isTrainingEnd = false
        second = 0
        nanoSecond = 0
        count = 0
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
                nanoSecond = now.nano
            }
            // 시간 초과 종료
            isTrainingEnd = true
            trainingEnd()
        }
    }

    private fun trainingEnd() {
        // 훈련 종료 후 결과 저장
        println(String.format("훈련 종료 후 저장 완료! : %d 번!", count))
    }

    fun screenClick(navigate : () ->Unit) {
        if (isTrainingEnd) {
            navigate()
        } else {
            count++
        }
    }
}