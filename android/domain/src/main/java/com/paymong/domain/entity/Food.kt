package com.paymong.domain.entity

import java.time.LocalDateTime

data class Food(
    val name: String,
    val foodCode: String,
    val price: Int,
    val lastBuy: LocalDateTime?
)
