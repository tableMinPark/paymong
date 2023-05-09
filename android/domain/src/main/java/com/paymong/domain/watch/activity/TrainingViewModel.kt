package com.paymong.domain.watch.activity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.ManagementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class TrainingViewModel : ViewModel() {
    var isTrainingEnd by mutableStateOf(false)
    var second by mutableStateOf(0)
    var nanoSecond by mutableStateOf(0)
    var count by mutableStateOf(0)

    private lateinit var timer : Job
    private var nowTime : Long = 0
    private var interval : Long = 10
    private val maxTime : Long = 5000  // 10000

    private var managementRepository: ManagementRepository = ManagementRepository()

    fun trainingInit() {
        if(!isTrainingEnd) {
            second = 0
            nanoSecond = 0
            count = 0
            nowTime = 0
            timerStart()
        }
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
        viewModelScope.launch {
            managementRepository.training(count)
                .catch {
                    it.printStackTrace()
                }
                .collect{
                }
        }
    }

    fun screenClick(navigate : () ->Unit) {
        if (isTrainingEnd) {
            navigate()
        } else {
            count++
        }
    }
}