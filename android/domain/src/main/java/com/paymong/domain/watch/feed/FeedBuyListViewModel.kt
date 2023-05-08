package com.paymong.domain.watch.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.ManagementRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import com.paymong.domain.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch

class FeedBuyListViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {
    var payPoint by mutableStateOf(0)
    var name by mutableStateOf("")
    var foodCode by mutableStateOf("")
    var price by mutableStateOf(0)
    var isCanBuy by mutableStateOf(false)

    private lateinit var load : Job
    private var foodList = mutableStateListOf<Food>()
    private var currentFoodPosition by mutableStateOf(0)

    private val managementRepository: ManagementRepository = ManagementRepository()
    var foodCategory by mutableStateOf("")

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            val foodCategory = stateHandle.get<String>("foodCategory") ?: throw IllegalStateException("No categoryId was passed to destination.")
            println(foodCategory)
            payPoint = 100000


            changeCurrentFoodPosition()
        }
    }

    fun getFoodList(foodCategory: String){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.getFoodList(foodCategory)
                .catch {
                    it.printStackTrace()
                }
                .collect{data ->
                    foodList.clear()
                    for(i in data.indices){
                        foodList.add(Food(data[i].name, data[i].foodCode, data[i].price, data[i].lastBuy))
                    }
                }
        }
    }

    fun prevButtonClick() {
        currentFoodPosition--
        if (currentFoodPosition < 0)
            currentFoodPosition = foodList.size - 1
        changeCurrentFoodPosition()
    }

    fun nextButtonClick() {
        currentFoodPosition++
        if (currentFoodPosition >= foodList.size)
            currentFoodPosition = 0
        changeCurrentFoodPosition()
    }

    fun selectButtonClick() {
        println(foodList[currentFoodPosition])
        println("선택완료!")
    }

    private fun changeCurrentFoodPosition() {
        val nowFood = foodList[currentFoodPosition]
        name = nowFood.name
        foodCode = nowFood.foodCode
        price = nowFood.price
        isCanBuy = Duration.between(nowFood.lastBuy, LocalDateTime.now()).seconds >= 600
    }
}