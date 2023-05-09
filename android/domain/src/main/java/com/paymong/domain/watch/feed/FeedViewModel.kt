package com.paymong.domain.watch.feed

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.data.model.response.FoodResDto
import com.paymong.data.repository.AuthRepository
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class FeedViewModel : ViewModel() {
    var payPoint by mutableStateOf(0)
    var name by mutableStateOf("")
    var foodCode by mutableStateOf("")
    var price by mutableStateOf(0)
    var isCanBuy by mutableStateOf(true)

    var foodList = mutableListOf<Food>()
    var currentFoodPosition by mutableStateOf(0)

    private val memberRepository: MemberRepository = MemberRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()
    var foodCategory by mutableStateOf("")
    var isClick by mutableStateOf(false)

    init {
        viewModelScope.launch {
            getPoint()
        }
    }

    fun getPoint(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect{ data ->
                    payPoint = data.point.toInt()
                }
        }
    }

    fun getFoodList(foodCategory: String){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.getFoodList(foodCategory)
                .catch {
                    it.printStackTrace()
                }
                .collect{
                        data ->
                        foodList.clear()
                        for(i in data.indices){
                            foodList.add(Food(data[i].name, data[i].foodCode, data[i].price, data[i].lastBuy))
                        }
                    changeCurrentFoodPosition()
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
        if(foodCategory == "FD"){
            viewModelScope.launch(Dispatchers.IO) {
                managementRepository.eatFood(foodList[currentFoodPosition].foodCode)
                    .catch {
                        it.printStackTrace()
                    }
                    .collect{
                        Log.d("buy food", foodList[currentFoodPosition].toString())
                    }
            }
        } else{ // snack
            viewModelScope.launch(Dispatchers.IO) {
                managementRepository.eatSnack(foodList[currentFoodPosition].foodCode)
                    .catch {
                        it.printStackTrace()
                    }
                    .collect{
                        Log.d("buy snack", foodList[currentFoodPosition].toString())
                    }
            }
        }
    }

    private fun changeCurrentFoodPosition() {
        val nowFood = foodList[currentFoodPosition]
        name = nowFood.name
        foodCode = nowFood.foodCode
        price = nowFood.price
        if(nowFood.lastBuy!=null) {
            isCanBuy = Duration.between(nowFood.lastBuy, LocalDateTime.now()).seconds >= 600
        }
    }
}