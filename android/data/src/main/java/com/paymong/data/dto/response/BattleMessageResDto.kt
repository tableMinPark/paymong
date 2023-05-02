package com.paymong.data.dto.response

data class BattleMessageResDto(
    val battleRoomId: String,
    val nowTurn: Int,
    val totalTurn: Int,
    val nextAttacker: String,
    val order: String,
    val damageA: Double,
    val damageB: Double,
    val healthA: Double,
    val healthB: Double
)