package com.paymong.domain.entity

data class MongSetting(
    val name : String,
    val sleepStart: String,
    val sleepEnd: String
) {
    constructor() : this("", "23:00", "7:00")
}
