package com.paymong.domain.watch.activity

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
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

class TrainingViewModel (application: Application): AndroidViewModel(application) {

    private var context : Context

    var isTrainingEnd by mutableStateOf(false)
    var second by mutableStateOf(0)
    var nanoSecond by mutableStateOf(0)
    var count by mutableStateOf(0)

    private lateinit var timer : Job
    private var nowTime : Long = 0
    private var interval : Long = 10
    private val maxTime : Long = 5000  // 10000

    private var managementRepository: ManagementRepository = ManagementRepository()

    var buttonSound by mutableStateOf(0)
    var winSound by mutableStateOf(0)
    var loseSound by mutableStateOf(0)

    val soundPool = SoundPool.Builder()
        .setMaxStreams(1) // 동시에 재생 가능한 스트림의 최대 수
        .build()

    init {
        context = application
        buttonSound()
    }

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
            isTrainingEnd = false
            navigate()
        } else {
            count++
        }
    }

    fun buttonSound() {
            buttonSound = soundPool.load(context, com.paymong.common.R.raw.button_sound, 1)
            winSound = soundPool.load(context, com.paymong.common.R.raw.win_sound, 1)
            loseSound = soundPool.load(context, com.paymong.common.R.raw.lose_sound, 1)
    }
}