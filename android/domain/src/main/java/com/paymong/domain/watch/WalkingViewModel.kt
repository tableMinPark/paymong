package com.paymong.domain.watch

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.WalkingCode
import com.paymong.data.repository.DataApplicationRepository
import com.paymong.data.repository.ManagementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class WalkingViewModel (
    private val application: Application
): AndroidViewModel(application) {
    var walkingState by mutableStateOf(WalkingCode.READY)
    var walkMinute by mutableStateOf(0L)
    var walkSecond by mutableStateOf(0L)
    var walkCount by mutableStateOf(0)

    private var isWalking : Boolean = false
    private var startTime : LocalDateTime = LocalDateTime.now()
    private var startCount : Int = -1
    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensor: Sensor
    private lateinit var stepSensorEventListener: SensorEventListener
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    private var managementRepository: ManagementRepository = ManagementRepository()
    private var dataApplicationRepository : DataApplicationRepository = DataApplicationRepository()

    init {
        Log.d("walkingViewModel", "init")

        try {
            val walkingData = dataApplicationRepository.getValue("isWalking")
            // 데이터가 있는 경우
            if (walkingData == "true") {
                isWalking = true
                val countData = dataApplicationRepository.getValue("startCount")
                val timeData = dataApplicationRepository.getValue("startTime")
                // 걸음을 걷고 있는 경우
                startCount =  countData.toInt()
                startTime = LocalDateTime.parse(timeData, formatter)
            }
            // 데이터가 없는 경우
            else {
                startCount = -1
                startTime = LocalDateTime.now()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

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
                    // 처음이면 이면 초기화
                    if (!isWalking) {
                        startCount = event.values[0].toInt()
                        startTime = LocalDateTime.now()
                        isWalking = true
                        dataApplicationRepository.setValue("startCount", startCount.toString())
                        dataApplicationRepository.setValue("startTime", startTime.toString())
                        dataApplicationRepository.setValue("isWalking", "true")
                    }
                    val nowCount = event.values[0].toInt()
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
            while(walkingState != WalkingCode.WALKING_END) {
                if (walkingState == WalkingCode.PAUSE) {
                    delay(1000)
                    Log.d("walkingViewModel", "PAUSE : $startTime")
                    startTime = startTime.plusSeconds(1)
                    dataApplicationRepository.setValue("startTime", startTime.toString())
                }
                val nowTime = LocalDateTime.now()
                walkMinute = ChronoUnit.MINUTES.between(startTime, nowTime)
                walkSecond = ChronoUnit.SECONDS.between(startTime, nowTime.minusMinutes(walkMinute))
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
                    isWalking = false
                }
                .collect{data->
                    if (data.code != "201") watchViewModel.updateStates(data)
                    walkingState = WalkingCode.WALKING_END
                    dataApplicationRepository.setValue("startCount", "")
                    dataApplicationRepository.setValue("startTime", "")
                    dataApplicationRepository.setValue("isWalking", "false")
                    isWalking = false
                }
        }
    }
}

