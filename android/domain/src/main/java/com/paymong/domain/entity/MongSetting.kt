package com.paymong.domain.entity

import com.paymong.common.code.CharacterCode
import java.time.LocalDateTime

data class MongSetting(
    val name : String,
    val sleepStart: String,
    val sleepEnd: String
) {
    constructor() : this("", "23:00", "7:00")
}
