package com.paymong.domain.watch

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.ManagementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class TrainingViewModel (
    application: Application
): AndroidViewModel(application) {
    companion object {
        private const val MAX_TIME : Long = 10000
    }

    var isTrainingEnd by mutableStateOf(false)
    var second by mutableStateOf(0)
    var nanoSecond by mutableStateOf(0)
    var count by mutableStateOf(0)

    private var nowTime : Long = 0
    private var interval : Long = 10

    private var managementRepository: ManagementRepository = ManagementRepository()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            timerStart()
        }
    }

    fun addCount() {
        count++
    }

    // 훈련 시작
    private fun timerStart(){
        viewModelScope.launch {
            while(nowTime < MAX_TIME) {
                delay(interval)
                nowTime += interval

                val now = Instant.ofEpochMilli(nowTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
                second = now.second
                nanoSecond = now.nano
            }
            // 시간 초과 종료
            isTrainingEnd = true
        }
    }

    // 훈련 종료
    fun trainingEnd(watchViewModel: WatchViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.training(count)
                .catch {
                    it.printStackTrace()
                }
                .collect{data ->
                    if (data.code != "201") watchViewModel.updateStates(data)
                }
        }
    }
}