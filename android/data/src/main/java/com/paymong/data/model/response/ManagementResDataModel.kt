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

data class ManagementResDto(
    val code: String,
    val poopCount: Int,
    val message: String,
    val stateCode: String,
    val weight: Int,
    val health: Double,
    val satiety: Double,
    val strength: Double,
    val sleep: Double,
)

data class EvolutionResDto(
    val mongCode: String,
    val stateCode: String
)

data class GraduationResDto(
    val mongCode: String,
)

data class RealTimeResDto(
    val code: String,
    val message: String
)

data class ManagementRealTimeResDto(
    val code: String,
    val message: String,
    val poopCount: Int,
    val stateCode: String,
    val weight: Int,
    val health: Double,
    val satiety: Double,
    val strength: Double,
    val sleep: Double,
    val mongId: Long,
    val name: String,
    val mongCode: String,
    val born: LocalDateTime
)

data class MapRealTimeResDto(
    val code: String,
    val message: String,
    val mapCode: String
)

data class ThingsRealTimeResDto(
    val code: String,
    val message: String,
    val thingsCode: String
)

data class PayPointRealTimeResDto(
    val code: String,
    val message: String,
    val point: Int
)