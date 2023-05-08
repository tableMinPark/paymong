package com.paymong.domain.watch.feed

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.ManagementRepository
import com.paymong.domain.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class FeedViewModel : ViewModel() {
    var payPoint by mutableStateOf(0)
    var name by mutableStateOf("")
    var foodCode by mutableStateOf("")
    var price by mutableStateOf(0)
    var isCanBuy by mutableStateOf(false)

    var foodList = mutableStateListOf<Food>()
    private var currentFoodPosition by mutableStateOf(0)

    private val managementRepository: ManagementRepository = ManagementRepository()
    var foodCategory by mutableStateOf("")

    init {
        viewModelScope.launch {
            payPoint = 100000

//            changeCurrentFoodPosition()
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

    fun current(){
        changeCurrentFoodPosition()
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