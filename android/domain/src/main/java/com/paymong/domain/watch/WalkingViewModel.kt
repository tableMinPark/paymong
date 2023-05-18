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


        viewModelScope.launch(Dispatchers.Main) {
            try {
                val walkingData = dataApplicationRepository.getValue("isWalking")
                Log.d("walkingViewModel", "isWalking : $walkingData")
                // 데이터가 있는 경우
                if (walkingData == "true") {
                    isWalking = true
                    val countData = dataApplicationRepository.getValue("startCount")
                    val timeData = dataApplicationRepository.getValue("startTime")
                    Log.d("walkingViewModel", "countData : $countData")
                    Log.d("walkingViewModel", "timeData : $timeData")
                    // 걸음을 걷고 있는 경우
                    startCount = countData.toInt()
                    startTime = LocalDateTime.parse(timeData, formatter)
                    walkingState = WalkingCode.WALKING
                }
                // 데이터가 없는 경우
                else {
                    isWalking = false
                    delay(1000)
                    walkingState = WalkingCode.PAUSE
                }
                Log.d("walkingViewModel", "isWalking : $walkingData")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            setSensor()
        }
    }

    private fun setSensor() {
        // 센서 설정
        viewModelScope.launch(Dispatchers.Main) {
            sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            stepSensorEventListener = object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent) {
                    // 처음이면 이면 초기화
                    Log.d("walkingViewModel", "isWalking : $isWalking")
                    if (!isWalking) {
                        startCount = event.values[0].toInt()
                        startTime = LocalDateTime.now()
                        dataApplicationRepository.setValue("startCount", startCount.toString())
                        dataApplicationRepository.setValue("startTime", startTime.toString())
                        dataApplicationRepository.setValue("isWalking", "true")
                        Log.d("walkingViewModel", "startCount : $startCount")
                        Log.d("walkingViewModel", "startTime : $startTime")
                        Log.d("walkingViewModel", "isWalking : $isWalking")
                        isWalking = true
                        walkingState = WalkingCode.WALKING
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
        viewModelScope.launch(Dispatchers.IO) {
            var sec = 0L
            while(walkingState != WalkingCode.WALKING_END) {
                if (walkingState == WalkingCode.PAUSE) {
                    delay(1000)
                    sec++
                    continue
                } else if (sec > 0) {
                    startTime = startTime.plusSeconds(sec)
                    dataApplicationRepository.setValue("startTime", startTime.toString())
                    sec = 0
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

