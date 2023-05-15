package com.paymong.domain.watch

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.SoundPool
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.WalkingCode
import com.paymong.data.repository.ManagementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class WalkingViewModel (
    private val application: Application
): AndroidViewModel(application) {
    companion object {
        private var interval : Long = 10
    }

    var walkingState by mutableStateOf(WalkingCode.READY)
    var walkMinute by mutableStateOf(0)
    var walkSecond by mutableStateOf(0)
    var walkCount by mutableStateOf(0)

    private var walkNowTime : Long = 0
    private val walkMaxTime : Long = 86_400_000

    var startCount : Int = 0
    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensor: Sensor
    private lateinit var stepSensorEventListener: SensorEventListener

    private var managementRepository: ManagementRepository = ManagementRepository()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            setSensor()
        }
    }

    private fun setSensor() {
        // 센서 설정
        viewModelScope.launch {
            sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            stepSensorEventListener = object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent) {
                    if (startCount == 0) {
                        startCount = event.values[0].toInt()
                    }
                    var nowCount = event.values[0].toInt()
                    walkCount = nowCount - startCount
                }
            }
            sensorManager.registerListener(
                stepSensorEventListener,
                stepSensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
            walkTimerStart()
        }
    }
    private fun walkTimerStart(){
        walkingState = WalkingCode.WALKING
        viewModelScope.launch(Dispatchers.IO) {
            while(walkNowTime < walkMaxTime) {
                if (walkingState == WalkingCode.PAUSE) continue
                if (walkingState == WalkingCode.WALKING_END) break
                delay(interval)
                walkNowTime += interval

                val now = Instant.ofEpochMilli(walkNowTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
                walkMinute = now.minute
                walkSecond = now.second
            }
        }
    }
    fun walkingEnd(watchViewModel: WatchViewModel) {
        // 산책 종료 후 결과 저장
        viewModelScope.launch (Dispatchers.IO) {
            managementRepository.walking(walkCount)
                .catch {
                    it.printStackTrace()
                    walkingState = WalkingCode.WALKING_END
                }
                .collect{data->
                    if (data.code != "201") watchViewModel.updateStates(data)
                    walkingState = WalkingCode.WALKING_END
                }
        }
    }
}