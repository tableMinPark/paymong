package com.paymong.domain.entity

import com.paymong.common.code.ThingsCode
import java.time.LocalDateTime

data class Things(
    val thingsId: Long,
    val thingsCode: ThingsCode,
    val thingsName: String,
    val routine: String,
    val regDt: LocalDateTime
){
    constructor(): this(0,ThingsCode.ST000,"","null", LocalDateTime.now())
}

data class AddThingsDevice(
    val thingsCode: ThingsCode,
    var thingsName : String
)