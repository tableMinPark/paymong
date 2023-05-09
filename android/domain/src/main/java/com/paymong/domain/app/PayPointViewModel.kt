package com.paymong.domain.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.Collect
import com.paymong.domain.entity.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PayPointViewModel : ViewModel() {

    var payList = mutableListOf<Point>()
    private val memberRepository: MemberRepository = MemberRepository()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            findPointList()
        }
    }

    private fun findPointList() {
        viewModelScope.launch(Dispatchers.IO){
            memberRepository.pointList().catch {
                it.printStackTrace()
            }.collect {
                data ->
                payList.clear()
                for(i in data.indices){
                    payList.add(Point(data[i].pointHistoryId, data[i].point, data[i].action, data[i].regDt, data[i].memberId))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}