package com.paymong.domain.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.CollectRepository
import com.paymong.domain.entity.Collect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CollectMapViewModel : ViewModel() {
    private lateinit var load : Job
    var mapList = mutableListOf<Collect>()
    private var collectRepository: CollectRepository = CollectRepository()

    override fun onCleared() {
        super.onCleared()
    }

    fun map(){
        viewModelScope.launch(Dispatchers.IO){
            collectRepository.map().catch {
                it.printStackTrace()
            }.collect{
                data ->
                mapList.clear()
                for(i in 1..data.size){
                    mapList.add(Collect(data[i-1].isOpen, data[i-1].name, data[i-1].mapCode))
                }
            }
        }
    }
}
