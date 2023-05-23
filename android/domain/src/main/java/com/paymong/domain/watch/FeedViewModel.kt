package com.paymong.domain.watch

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.repository.ManagementRepository
import com.paymong.domain.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class FeedViewModel (
    application: Application
): AndroidViewModel(application) {
    var name by mutableStateOf("")
    var foodCode by mutableStateOf("")
    var price by mutableStateOf(0)
    var isCanBuy by mutableStateOf(false)
    private var foodList = mutableListOf<Food>()
    var currentFoodPosition by mutableStateOf(0)

    var foodCategory by mutableStateOf("")
    var currentCategory by mutableStateOf("")
    var isClick by mutableStateOf(false)

    private val managementRepository: ManagementRepository = ManagementRepository()

    fun reset() {
        name = ""
        foodCode = ""
        price = 0
        isCanBuy = false
        foodList.clear()
        currentFoodPosition = 0
        isClick = false
    }

    fun getFoodList(point: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.getFoodList(foodCategory)
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    for (i in data.indices) {
                        foodList.add(
                            Food(
                                data[i].name,
                                data[i].foodCode,
                                data[i].price,
                                data[i].lastBuy ?: LocalDateTime.now().minusMinutes(10)
                            )
                        )
                    }
                    changeCurrentFoodPosition(point)
                    currentCategory = foodCategory
                    foodCategory = ""
                }
        }
    }
    fun prevButtonClick(point: Int) {
        if (foodCode == "") return
        currentFoodPosition--
        if (currentFoodPosition < 0)
            currentFoodPosition = foodList.size - 1
        changeCurrentFoodPosition(point)
    }
    fun nextButtonClick(point: Int) {
        if (foodCode == "") return
        currentFoodPosition++
        if (currentFoodPosition >= foodList.size)
            currentFoodPosition = 0
        changeCurrentFoodPosition(point)
    }
    fun selectButtonClick(fc: String, watchViewModel: WatchViewModel) {
        if (foodCode == "") return
        // food
        if (fc == "FD") {
            viewModelScope.launch(Dispatchers.IO) {
                managementRepository.eatFood(foodList[currentFoodPosition].foodCode)
                    .catch {
                        it.printStackTrace()
                    }
                    .collect {data ->
                        watchViewModel.eating = true
                        if (data.code != "201") watchViewModel.updateStates(data)
                    }
            }
        }
        // snack
        else {
            viewModelScope.launch(Dispatchers.IO) {
                managementRepository.eatSnack(foodList[currentFoodPosition].foodCode)
                    .catch {
                        it.printStackTrace()
                    }
                    .collect {data ->
                        watchViewModel.eating = true
                        if (data.code != "201") watchViewModel.updateStates(data)
                    }
            }
        }
    }
    private fun changeCurrentFoodPosition(point: Int) {
        val nowFood = foodList[currentFoodPosition]
        name = nowFood.name
        foodCode = nowFood.foodCode
        price = nowFood.price

        isCanBuy = if (nowFood.lastBuy == null && nowFood.price <= point) true
        else Duration.between(nowFood.lastBuy, LocalDateTime.now()).seconds >= 600 && nowFood.price <= point
    }
}