package com.paymong.data.model.response

import java.time.LocalDateTime

data class CreateResDto(
    val name: String,
    val mongCode: String,
    val weight: Int,
    val born: LocalDateTime
) {
    constructor() : this("", "",0,LocalDateTime.now())
}
