package com.paymong.domain.watch.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.CharacterCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.data.repository.InformationRepository
import com.paymong.domain.entity.Mong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var mapCode by mutableStateOf(MapCode.MP000)
    private val informationRepository: InformationRepository = InformationRepository()

    init {
        viewModelScope.launch {
            findMong()
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("MainViewModel 끝")
    }

    private fun findMong() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMong()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mapCode = MapCode.valueOf(data.mapCode ?: "MP000")
                }
        }
    }
}