package com.paymong.domain.entity

import java.time.LocalDateTime

data class Point(
    val pointHistoryId : Long,
    val point: Int,
    val action: String,
    val regDt: LocalDateTime,
    val memberId: String
)
