package com.paymong.data.model.response

data class LoginResDto(
    val accessToken: String,
    val refreshToken: String
) {
    constructor() : this("", "")
}
