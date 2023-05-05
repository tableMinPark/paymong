package com.paymong.domain.entity

data class MongStats(
    val mongId: Long = 0L,
    val name: String = "",
    val health: Float = 0.0F,
    val satiety: Float = 0.0F,
    val strength: Float = 0.0F,
    val sleep: Float = 0.0F
)
