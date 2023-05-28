package com.paymong.domain.app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.CollectRepository
import com.paymong.domain.entity.Collect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CollectPayMongViewModel : ViewModel() {
    var isLoading = mutableStateOf(false)
    var mongList = mutableListOf<Collect>()
    var openList = mutableListOf<Collect>()

    private var collectRepository: CollectRepository = CollectRepository()

    fun init(){
        viewModelScope.launch(Dispatchers.IO){
            collectRepository.mong().catch {
                it.printStackTrace()
            }.collect{
                data ->
                mongList.clear()
                openList.clear()
                for(i in data.indices){
                    mongList.add(Collect(data[i].isOpen, data[i].name, data[i].characterCode))
                    if(data[i].isOpen) {
                        openList.add(Collect(data[i].isOpen, data[i].name, data[i].characterCode))
                    }
                }
                isLoading.value = true
            }
        }
    }
}
