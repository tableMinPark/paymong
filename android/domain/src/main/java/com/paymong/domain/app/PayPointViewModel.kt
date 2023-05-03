package com.paymong.domain.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.domain.entity.Point
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PayPointViewModel : ViewModel() {

    private lateinit var load : Job
    var payList = mutableListOf<Point>()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            findPointList()
        }
    }

    private fun findPointList() {
        payList.add(Point("페이 결제", 240))
        payList.add(Point("사과 구매", -100))
        payList.add(Point("훈련", -50))
    }

    override fun onCleared() {
        super.onCleared()
    }
}