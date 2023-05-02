package com.paymong.domain.app.paypoint

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.domain.entity.Point
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PayPointViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {

    private lateinit var load : Job
    private lateinit var memberId : String
    var payList = mutableListOf<Point>()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            memberId = stateHandle.get<String>("memberId")
                ?: throw IllegalStateException("No memberId was passed to destination.")
            println(memberId)

            payList.add(Point("페이 결제", 240))
            payList.add(Point("사과 구매", -100))
            payList.add(Point("훈련", -50))
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}