package com.paymong.data.model.response

data class BattleErrorResDto(
    var code: String,
    val message: String
)

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