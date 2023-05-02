package com.paymong.common.entity

import java.time.LocalDateTime

data class FoodEntity(
    val name: String,
    val foodCode: String,
    val price: Int,
    val lastBuy: LocalDateTime
)
