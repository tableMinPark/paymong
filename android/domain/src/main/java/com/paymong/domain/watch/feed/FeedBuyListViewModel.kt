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
import com.paymong.common.entity.FoodEntity

class FeedBuyListViewModel constructor(
    private val stateHandle: SavedStateHandle,
): ViewModel() {
    var payPoint by mutableStateOf(0)
    var name by mutableStateOf("")
    var foodCode by mutableStateOf("")
    var price by mutableStateOf(0)
    var isCanBuy by mutableStateOf(false)

    private lateinit var load : Job
    private var foodEntityList = mutableStateListOf<FoodEntity>()
    private var currentFoodPosition by mutableStateOf(0)

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {
            val foodCategory = stateHandle.get<String>("foodCategory") ?: throw IllegalStateException("No categoryId was passed to destination.")
            println(foodCategory)
            payPoint = 100000

            // meal
            foodEntityList.add(FoodEntity("별사탕", "FD000", 3, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("사과", "FD001", 500, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("삼각김밥", "FD002", 500, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("샌드위치", "FD003", 500, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("피자", "FD004", 1000, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("닭다리", "FD005", 1000, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("스테이크", "FD006", 1000, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("우주식품", "FD007", 5000, LocalDateTime.now()))
            // snack
            foodEntityList.add(FoodEntity("초콜릿", "SN000", 300, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("사탕", "SN001", 300, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("음료수", "SN002", 300, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("쿠키", "SN003", 600, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("케이크", "SN004", 600, LocalDateTime.now()))
            foodEntityList.add(FoodEntity("감자튀김", "SN005", 600, LocalDateTime.now()))

            changeCurrentFoodPosition()
        }
    }

    fun prevButtonClick() {
        currentFoodPosition--
        if (currentFoodPosition < 0)
            currentFoodPosition = foodEntityList.size - 1
        changeCurrentFoodPosition()
    }

    fun nextButtonClick() {
        currentFoodPosition++
        if (currentFoodPosition >= foodEntityList.size)
            currentFoodPosition = 0
        changeCurrentFoodPosition()
    }

    fun selectButtonClick() {
        println(foodEntityList[currentFoodPosition])
        println("선택완료!")
    }

    private fun changeCurrentFoodPosition() {
        val nowFood = foodEntityList[currentFoodPosition]
        name = nowFood.name
        foodCode = nowFood.foodCode
        price = nowFood.price
        isCanBuy = Duration.between(nowFood.lastBuy, LocalDateTime.now()).seconds >= 600
    }
}