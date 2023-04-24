package com.paymong.domain.watch.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class WalkingViewModel : ViewModel() {
    var isWalkingEnd by mutableStateOf(false)
    var minute by mutableStateOf(0)
    var second by mutableStateOf(0)
    var count by mutableStateOf(0)
    var startCount : Int = 0

    private lateinit var timer : Job
    private var nowTime : Long = 0
    private var interval : Long = 10
    private val maxTime : Long = 86_400_000

    private lateinit var ctx : Context
    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensor: Sensor
    private lateinit var stepSensorEventListener: SensorEventListener

    init {
        // 값 초기화
        isWalkingEnd = false
        minute = 0
        second = 0
        count = 0
        nowTime = 0
    }

    fun setSensor(context : Context) {
        // 센서 설정
        ctx = context
        sensorManager = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        stepSensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) { }
            override fun onSensorChanged(event: SensorEvent) {
                event?.let {
                    if (startCount == 0) {
                        startCount = event.values[0].toInt()
                    }
                    var nowCount = event.values[0].toInt()
                    count = nowCount - startCount
//                    count += event.values[0].toInt()
                }
            }
        }
        sensorManager.registerListener(stepSensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)

        // 타이머 시작
        timerStart()
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(stepSensorEventListener)
    }

    private fun timerStart(){
        if(::timer.isInitialized) timer.cancel()

        timer = viewModelScope.launch {
            while(nowTime < maxTime && !isWalkingEnd) {
                delay(interval)
                nowTime += interval

                val now = Instant.ofEpochMilli(nowTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
                minute = now.minute
                second = now.second
            }
            // 시간 초과 종료
            isWalkingEnd = true
            walkingEnd()
        }
    }

    private fun walkingEnd() {
        // 산책 종료 후 결과 저장
        isWalkingEnd = true
        println(String.format("산책 종료 후 저장 완료! : %d 걸음!", count))
    }

    fun screenClick(navigate : () ->Unit) {
        if (isWalkingEnd){
            navigate()
        } else {
            // 사용자 선택 종료
            walkingEnd()
        }
    }
}