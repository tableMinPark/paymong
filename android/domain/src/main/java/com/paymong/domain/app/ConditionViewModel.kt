package com.paymong.domain.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.InformationRepository
import com.paymong.domain.entity.MongStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ConditionViewModel : ViewModel() {
    var mongStats by mutableStateOf(MongStats())

    private val informationRepository: InformationRepository = InformationRepository()

    private fun findMongCondition() {
        viewModelScope.launch(Dispatchers.IO) {
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            findMongCondition()
        }
    }
}