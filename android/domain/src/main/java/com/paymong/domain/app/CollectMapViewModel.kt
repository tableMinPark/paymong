package com.paymong.domain.app

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.CollectRepository
import com.paymong.domain.entity.Collect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CollectMapViewModel : ViewModel() {
    var mapList = mutableListOf<Collect>()
    var openList = mutableListOf<Collect>()
    var success = mutableStateOf(false)
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
                openList.clear()
                for(i in data.indices){
                    mapList.add(Collect(data[i].isOpen, data[i].name, data[i].mapCode))
                    if(data[i].isOpen) {
                        openList.add(Collect(data[i].isOpen, data[i].name, data[i].mapCode))
                    }
                }
                success.value = true
            }
        }
    }
}
