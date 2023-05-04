package com.paymong.domain.entity

import com.paymong.common.code.CharacterCode
import java.time.LocalDateTime

data class MongInfo(
    val name : String,
    val mongCode : CharacterCode,
    val weight : Int,
    val born : LocalDateTime

) {
    constructor() : this("", CharacterCode.CH000,0, LocalDateTime.now())
}
