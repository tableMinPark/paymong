package com.paymong.data.model.response

data class LoginResDto(
    val accessToken: String,
    val refreshToken: String
) {
    constructor() : this("", "")
}

data class ReissueResDto(
    val accessToken: String
) {
    constructor() : this("")
}
