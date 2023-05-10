package com.paymong.domain.watch

import android.app.Application
import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.ManagementRepository
import com.paymong.domain.entity.Mong
import com.paymong.domain.entity.MongInfo
import com.paymong.domain.entity.MongStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WatchViewModel (
    private val application: Application
): AndroidViewModel(application) {
    var point by mutableStateOf(0)

    var mong by mutableStateOf(Mong())

    var mongStats by mutableStateOf(MongStats())

    var mongInfo by mutableStateOf(MongInfo())
    var age by mutableStateOf("")

    var stateCode by mutableStateOf(MongStateCode.CD000)
    var poopCount by mutableStateOf(0)
    var mapCode by mutableStateOf(MapCode.MP000)

    private val informationRepository: InformationRepository = InformationRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()

    var isHappy by mutableStateOf(false)
    var isClicked by mutableStateOf(false)

    var buttonSound by mutableStateOf(0)
    val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(1) // 동시에 재생 가능한 스트림의 최대 수
        .build()

    init {
        buttonSound()
        findMong()
        findMongCondition()
        findMongInfo()
    }
    
    private fun findMong() {
        viewModelScope.launch(Dispatchers.Main) {
            informationRepository.findMong()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mong = Mong(
                        data.mongId,
                        data.name,
                        CharacterCode.valueOf(data.mongCode)
                    )
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    poopCount = data.poopCount
                    mapCode = MapCode.valueOf(data.mapCode ?: "MP000")
                }
        }
    }

    private fun findMongCondition() {
        viewModelScope.launch(Dispatchers.Main) {
            informationRepository.findMongStats()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mongStats = MongStats(
                        data.mongId,
                        data.name,
                        data.health,
                        data.satiety,
                        data.strength,
                        data.sleep,
                    )
                }
        }
    }

    private fun findMongInfo() {
        viewModelScope.launch(Dispatchers.Main) {
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

    private fun calcAge(): String {
        val day = ChronoUnit.DAYS.between(mongInfo.born, LocalDateTime.now())
        val hours = ChronoUnit.HOURS.between(mongInfo.born.plusDays(day), LocalDateTime.now())
        val minutes = ChronoUnit.MINUTES.between(mongInfo.born.plusDays(day).plusHours(hours), LocalDateTime.now())
        return String.format("%d일 %d시간 %d분", day, hours, minutes)
    }

    fun stroke(){
        viewModelScope.launch(Dispatchers.Main) {
            managementRepository.stroke()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                    data ->
                    isHappy = data.code=="200"
                }
        }
    }

    fun sleep(){
        viewModelScope.launch(Dispatchers.Main) {
            managementRepository.sleep()
                .catch {
                    it.printStackTrace()
                }
                .collect{}
        }
    }

    fun poop(){
        viewModelScope.launch(Dispatchers.Main) {
            managementRepository.poop()
                .catch {
                    it.printStackTrace()
                }
                .collect{}
        }
    }

    private fun buttonSound() {
        buttonSound = soundPool.load(application, com.paymong.common.R.raw.button_sound, 1)
    }
}