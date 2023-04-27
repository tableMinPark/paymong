package com.paymong.data.watch.dto.response

data class BattleMessageResDto(
    val battleRoomId: String,
    val nowTurn: Int,
    val nextAttacker: String,
    val order: String,
    val damageA: Double,
    val damageB: Double,
    val healthA: Double,
    val healthB: Double
)
