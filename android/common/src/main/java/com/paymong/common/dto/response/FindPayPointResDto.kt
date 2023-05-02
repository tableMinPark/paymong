package com.paymong.common.dto.response

import com.google.gson.annotations.SerializedName

data class FindPayPointResDto(
    @SerializedName("payPoint")
    val payPoint: Int
)
