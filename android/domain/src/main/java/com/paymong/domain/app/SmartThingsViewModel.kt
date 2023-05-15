package com.paymong.domain.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.model.request.AddThingsReqDto
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.AddThingsDevice
import com.paymong.domain.entity.Things
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SmartThingsViewModel : ViewModel() {
    var connectedThingsList = mutableListOf<Things>()
    var thingsList = mutableListOf<AddThingsDevice>()
    var isSelect by mutableStateOf(-1)
    var routine by mutableStateOf("")

    var isAdd by mutableStateOf(false)
    var thingsId:Long by mutableStateOf(0)
    var isDelete by mutableStateOf(false)

    private var memberRepository: MemberRepository = MemberRepository()

    init {
        viewModelScope.launch(Dispatchers.IO){
            connectedThings()
        }
    }
    fun connectedThings(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findThings()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                    data ->
                    connectedThingsList.clear()
                    for(i in data.indices){
                        connectedThingsList.add(Things(data[i].thingsId, data[i].thingsCode, data[i].thingsName, data[i].routine, data[i].regDt))
                    }
                }
        }
    }

    fun toConnectThings(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.addFindThings()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                    data ->
                    thingsList.clear()
                    for(i in data.indices){
                        thingsList.add(AddThingsDevice(data[i].thingsCode, data[i].thingsName))
                    }
                }
        }
    }

    fun addThings(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.addThings(AddThingsReqDto(thingsList[isSelect].thingsName, thingsList[isSelect].thingsCode, routine))
                .catch {
                    it.printStackTrace()
                }
                .collect{
                }
        }
    }

    fun deleteThings(){
        viewModelScope.launch(Dispatchers.IO){
            memberRepository.deleteThings(thingsId)
                .catch {
                    it.printStackTrace()
                }
                .collect{
                }
        }
    }
}