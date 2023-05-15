package com.paymong.domain.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.InformationRepository
import com.paymong.domain.entity.MongInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class InfoDetailViewModel : ViewModel() {

    var mongInfo by mutableStateOf(MongInfo())
    var age by mutableStateOf("")

    private val informationRepository: InformationRepository = InformationRepository()

    // 몽 상세
    private fun findMongInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMongInfo()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mongInfo = MongInfo(
                        data.weight,
                        data.born
                    )
                }
            while(true) {
                age = calcAge()
                delay(6000)
            }
        }
    }

    // 나이계산 후 string 으로 반환
    private fun calcAge(): String {
        val day = ChronoUnit.DAYS.between(mongInfo.born, LocalDateTime.now())
        val hours = ChronoUnit.HOURS.between(mongInfo.born.plusDays(day), LocalDateTime.now())
        val minutes = ChronoUnit.MINUTES.between(mongInfo.born.plusDays(day).plusHours(hours), LocalDateTime.now())
        return String.format("%d일 %d시간 %d분", day, hours, minutes)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            findMongInfo()
        }
    }
}