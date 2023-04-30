package com.paymong.common.entity

data class BattleActiveEntity(
    val battleRoomId: String,
    val nowTurn: Int,
    val totalTurn: Int,
    val nextAttacker: String,
    val order: String,
    val damageA: Double,
    val damageB: Double,
    val healthA: Double,
    val healthB: Double
) {
    constructor() : this("", 0, 0, "", "", 0.0, 0.0, 0.0, 0.0)
}
