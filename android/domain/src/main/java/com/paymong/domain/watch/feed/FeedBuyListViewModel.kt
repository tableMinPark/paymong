package com.paymong.domain.watch.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import com.paymong.data.watch.dto.response.Food

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
            payPoint = 100000

            // meal
            foodList.add(Food("별사탕", "FD000", 3, LocalDateTime.now()))
            foodList.add(Food("사과", "FD001", 500, LocalDateTime.now()))
            foodList.add(Food("삼각김밥", "FD002", 500, LocalDateTime.now()))
            foodList.add(Food("샌드위치", "FD003", 500, LocalDateTime.now()))
            foodList.add(Food("피자", "FD004", 1000, LocalDateTime.now()))
            foodList.add(Food("닭다리", "FD005", 1000, LocalDateTime.now()))
            foodList.add(Food("스테이크", "FD006", 1000, LocalDateTime.now()))
            foodList.add(Food("우주식품", "FD007", 5000, LocalDateTime.now()))
            // snack
            foodList.add(Food("초콜릿", "SN000", 300, LocalDateTime.now()))
            foodList.add(Food("사탕", "SN001", 300, LocalDateTime.now()))
            foodList.add(Food("음료수", "SN002", 300, LocalDateTime.now()))
            foodList.add(Food("쿠키", "SN003", 600, LocalDateTime.now()))
            foodList.add(Food("케이크", "SN004", 600, LocalDateTime.now()))
            foodList.add(Food("감자튀김", "SN005", 600, LocalDateTime.now()))

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