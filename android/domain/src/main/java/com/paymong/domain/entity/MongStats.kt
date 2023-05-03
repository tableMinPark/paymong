package com.paymong.domain.entity

data class MongStats(
    val name: String,
    val health: Float,
    val satiety: Float,
    val strength: Float,
    val sleep: Float
) {
    constructor() : this("", 0.0F, 0.0F, 0.0F, 0.0F)
}
