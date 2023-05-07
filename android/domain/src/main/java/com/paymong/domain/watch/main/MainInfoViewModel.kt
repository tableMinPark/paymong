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

class MainInfoViewModel : ViewModel() {
    var characterCode by mutableStateOf("")
    var poopCount by mutableStateOf(0)

    var mong by mutableStateOf(Mong())
    var stateCode by mutableStateOf(MongStateCode.CD000)
    private val informationRepository: InformationRepository = InformationRepository()

    init {
        viewModelScope.launch {
            findMong()
        }
    }

    private fun findMong() {
        viewModelScope.launch(Dispatchers.IO) {
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
                }
        }
    }
}