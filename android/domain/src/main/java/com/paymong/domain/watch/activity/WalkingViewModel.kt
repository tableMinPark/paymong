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
import com.paymong.data.repository.MemberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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

    private var managementRepository: ManagementRepository = ManagementRepository()

    init {
        // 값 초기화
        isWalkingEnd = false
        minute = 0
        second = 0
        count = 0
        nowTime = 0
        timerStart()
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
                if (startCount == 0) {
                    startCount = event!!.values[0].toInt()
                }
                var nowCount = event!!.values[0].toInt()
                count = nowCount - startCount
//                    count += event.values[0].toInt()
            }
        }
        sensorManager.registerListener(stepSensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)

        // 타이머 시작
        timerStart()
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
        viewModelScope.launch {
            managementRepository.walking(count)
                .catch {
                    it.printStackTrace()
                }
                .collect{
                }
        }
    }

    fun screenClick(navigate : () ->Unit) {
        if (isWalkingEnd){
            navigate()
        } else {
            // 사용자 선택 종료
            walkingEnd()
        }
    }

    override fun onCleared() {
        super.onCleared()
//        sensorManager.unregisterListener(stepSensorEventListener)
        // sensorManager 초기값없다고 터짐요
    }
}