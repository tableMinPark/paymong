package com.paymong.data.model.response

import com.paymong.common.code.CharacterCode
import com.paymong.common.code.ThingsCode
import java.time.LocalDateTime

data class FindMemberResDto(
    val mongCode : String,
    val point : Long
)

data class PointInfoResDto(
    val content : String,
    val price : Int
)

data class ThingsResDto(
    val thingsId : Long,
    val thingsCode : ThingsCode,
    val thingsName : String,
    val routine : String,
    val regDt : LocalDateTime,
)

data class AddThingsResDto(
    val thingsCode : ThingsCode,
    val thingsName : String
)