package com.paymong.domain.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.CollectRepository
import com.paymong.domain.entity.Collect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CollectPayMongViewModel : ViewModel() {

    var mongList = mutableListOf<Collect>()
    private var collectRepository: CollectRepository = CollectRepository()

    init {
        viewModelScope.launch {

//            for(i in 0..5){
//                val index = i.toString()
//                mongList.add(Collect(false,"", "CH00${index}"))
//            }
//            for(i in 0..2){
//                val index = i.toString()
//                mongList.add(Collect(true,"", "CH10${index}"))
//            }
//            for(i in 0..2){
//                val index = i.toString()
//                for(j in 0..3){
//                    val middle = j.toString()
//                    mongList.add(Collect(true,"", "CH2${middle}${index}"))
//                }
//            }
//            for(i in 0..2){
//                val index = i.toString()
//                for(j in 0..3){
//                    val middle = j.toString()
//                    mongList.add(Collect(true,"", "CH3${middle}${index}"))
//                }
//            }
//            mongList.add(Collect(true,"", "CH203"))
//            mongList.add(Collect(true,"", "CH303"))
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

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
            }
        }
    }
}
