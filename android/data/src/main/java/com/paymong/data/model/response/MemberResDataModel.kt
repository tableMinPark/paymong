package com.paymong.data.model.response

import com.paymong.common.code.ThingsCode
import java.time.LocalDateTime

data class FindMemberResDto(
    val mongCode : String,
    val point : Int
)

data class PointInfoResDto(
    val pointHistoryId : Long,
    val point: Int,
    val action: String,
    val regDt: LocalDateTime,
    val memberId: String
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

data class AddRoutineResDto(
    val thingsCode: String
)