package com.paymong.domain.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.CollectRepository
import com.paymong.domain.entity.Collect
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CollectMapViewModel : ViewModel() {
    private lateinit var load : Job
    var mapList = mutableListOf<Collect>()

    private var collectRepository: CollectRepository = CollectRepository()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {

//            for(i in 1..32){
//                val index = i.toString()
//                if(i<10){
//                    mapList.add("MP00${index}")
//                } else{
//                    mapList.add("MP0${index}")
//                }
//            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun map(){
        var res = collectRepository.map()
        if(res!=null){
            for(i in 0..res.size){
                mapList.add(Collect(res[i].isOpen, res[i].name, res[i].mapCode))
            }
        }
    }
}
