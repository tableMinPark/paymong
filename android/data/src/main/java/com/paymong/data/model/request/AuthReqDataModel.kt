package com.paymong.data.model.request

data class LoginReqDto(
    val playerId: String
)

data class ReissueReqDto(
    val refreshToken: String
)
