package com.paymong.data.model.request

import com.paymong.common.code.ThingsCode

data class AddThingsReqDto(
    val thingsName : String,
    val thingsCode : ThingsCode,
    val routine : String
)
