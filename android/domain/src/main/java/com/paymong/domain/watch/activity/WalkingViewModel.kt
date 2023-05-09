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

class WalkingViewModel : ViewModel() {
    private lateinit var timer : Job
    private var interval : Long = 10

    var isWalkingEnd by mutableStateOf(false)
    var realWalkingEnd by mutableStateOf(false)
    var walkMinute by mutableStateOf(0)
    var walkSecond by mutableStateOf(0)
    var walkCount by mutableStateOf(0)
    private var walkNowTime : Long = 0
    private val walkMaxTime : Long = 86_400_000

    var startCount : Int = 0
    private lateinit var ctx : Context
    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensor: Sensor
    private lateinit var stepSensorEventListener: SensorEventListener

    private var managementRepository: ManagementRepository = ManagementRepository()

    fun walkingInit(){
        if(!isWalkingEnd) {
            walkMinute = 0
            walkSecond = 0
            walkCount = 0
            walkNowTime = 0
            walkTimerStart()
        }
    }

    private fun walkTimerStart(){
        if(::timer.isInitialized) timer.cancel()

        timer = viewModelScope.launch {
            while(walkNowTime < walkMaxTime && !isWalkingEnd) {
                delay(interval)
                walkNowTime += interval

                val now = Instant.ofEpochMilli(walkNowTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
                walkMinute = now.minute
                walkSecond = now.second
            }
            // 시간 초과 종료
            isWalkingEnd = true
        }
    }

    fun walkingEnd() {
        // 산책 종료 후 결과 저장
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.walking(walkCount)
                .catch {
                    it.printStackTrace()
                }
                .collect{
                }
        }
    }

//    fun setSensor(context : Context) {
//        // 센서 설정
//        ctx = context
//        sensorManager = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
////        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
//        stepSensorEventListener = object : SensorEventListener {
//            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) { }
//            override fun onSensorChanged(event: SensorEvent) {
//                if (startCount == 0) {
//                    startCount = event!!.values[0].toInt()
//                }
//                var nowCount = event!!.values[0].toInt()
//                walkCount = nowCount - startCount
////                    count += event.values[0].toInt()
//            }
//        }
//        sensorManager.registerListener(stepSensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
//
//        // 타이머 시작
//        walkTimerStart()
//    }

    override fun onCleared() {
        super.onCleared()
//        sensorManager.unregisterListener(stepSensorEventListener)
        // sensorManager 초기값없다고 터짐요
    }
}