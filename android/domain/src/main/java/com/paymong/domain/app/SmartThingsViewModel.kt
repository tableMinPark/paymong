package com.paymong.domain.app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.AddThings
import com.paymong.domain.entity.Things
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SmartThingsViewModel : ViewModel() {
    var connectedThingsList = mutableListOf<Things>()
    var thingsList = mutableListOf<AddThings>()
    var isSelect = mutableStateOf(false)
    var routine = mutableStateOf("")

    private var memberRepository: MemberRepository = MemberRepository()

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
                        thingsList.add(AddThings(data[i].thingsCode, data[i].thingsName))
                    }
                }
        }
    }
}