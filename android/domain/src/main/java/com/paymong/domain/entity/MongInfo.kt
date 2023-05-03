package com.paymong.domain.entity

import com.paymong.common.code.CharacterCode
import java.time.LocalDateTime

data class MongInfo(
    val name : String,
    val mongCode : CharacterCode,
    val born : LocalDateTime,
    val weight : Int
) {
    constructor() : this("", CharacterCode.CH000, LocalDateTime.now(), 0)
}
