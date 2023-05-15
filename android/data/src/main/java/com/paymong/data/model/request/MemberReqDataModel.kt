package com.paymong.data.model.request

import com.paymong.common.code.ThingsCode

data class AddPayReqDto(
    val content : String,
    val price : Int
)

data class AddThingsReqDto(
    val thingsName : String,
    val thingsCode : ThingsCode,
    val routine : String
)

data class AddRoutineReqDto(
    val routine : String
)