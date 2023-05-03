package com.paymong.domain.entity

data class Collect(
    val isOpen : Boolean,
    val name : String,
    val code : String
) {
    constructor() : this(false, "", "")
}
