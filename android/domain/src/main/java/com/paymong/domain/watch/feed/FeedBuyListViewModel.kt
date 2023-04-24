package com.paymong.domain.watch.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.watch.dto.response.Food
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

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

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            val foodCategory = stateHandle.get<String>("foodCategory") ?: throw IllegalStateException("No categoryId was passed to destination.")
            println(foodCategory)
            payPoint = 10000
            foodList.add(Food("사과", "FD100", 100, LocalDateTime.now()))
            foodList.add(Food("닭다리", "FD101", 500, LocalDateTime.now()))
            foodList.add(Food("케이크", "FD102", 1000, LocalDateTime.now()))
            changeCurrentFoodPosition()
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