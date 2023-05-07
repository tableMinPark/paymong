package com.paymong.domain.entity

import java.time.LocalDateTime

data class Things(
    val thingsId : Long,
    val thingsCode : String,
    val thingsName : String,
    val routine : String,
    val regDt : LocalDateTime
)

data class AddThings(
    val thingsName : String,
    var isSelect : Boolean
)