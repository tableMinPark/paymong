package com.paymong.common.dto.response

import com.google.gson.annotations.SerializedName

data class AddPayPointResDto(
    @SerializedName("payPoint")
    val payPoint: Int,
    @SerializedName("mapCode")
    val mapCode: String
)
