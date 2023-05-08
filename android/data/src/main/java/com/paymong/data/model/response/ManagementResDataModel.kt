package com.paymong.data.model.response

import java.time.LocalDateTime

data class AddMongResDto(
    val mongId: Long,
    val name: String,
    val mongCode: String,
    val weight: Int,
    val born: LocalDateTime
)

data class FindMongResDto(
    val mongId: Long,
    val name: String,
    val mongCode: String,
    val stateCode: String,
    val poopCount: Int,
    val mapCode: String
)

data class FindMongInfoResDto(
    val name: String,
    val weight: Int,
    val born: LocalDateTime
)

data class FindMongStatsResDto(
    val mongId: Long,
    val name: String,
    val health: Float,
    val satiety: Float,
    val strength: Float,
    val sleep: Float
)

data class FoodResDto(
    val name: String,
    val foodCode: String,
    val price: Int,
    val lastBuy: LocalDateTime
)