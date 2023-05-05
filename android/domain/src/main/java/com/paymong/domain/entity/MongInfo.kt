package com.paymong.domain.entity

import com.paymong.common.code.CharacterCode
import java.time.LocalDateTime

data class Mong(
    val mongId : Long = 0L,
    val name : String = "",
    val mongCode : CharacterCode = CharacterCode.CH444

)

data class MongInfo(
    val weight: Int = 0,
    val born: LocalDateTime = LocalDateTime.now()

)
