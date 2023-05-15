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

    var mongList = mutableListOf<Collect>()
    var success = mutableStateOf(false)
    private var collectRepository: CollectRepository = CollectRepository()

    fun mong(){
        viewModelScope.launch(Dispatchers.IO){
            collectRepository.mong().catch {
                it.printStackTrace()
            }.collect{
                data ->
                mongList.clear()
                for(i in data.indices){
                    mongList.add(Collect(data[i].isOpen, data[i].name, data[i].characterCode))
                }
                success.value = true
            }
        }
    }
}
