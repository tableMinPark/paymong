package com.paymong.data.model.response

data class BattleMessageResDto(
    val battleRoomId: String,
    val mongCodeA: String,
    val mongCodeB: String,
    val nowTurn: Int,
    val totalTurn: Int,
    val nextAttacker: String,
    val order: String,
    val damageA: Double,
    val damageB: Double,
    val healthA: Double,
    val healthB: Double
)