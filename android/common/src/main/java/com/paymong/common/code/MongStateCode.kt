package com.paymong.common.code

enum class MongStateCode(
    val code: String,
    val codeName: String,
) {
    CD000("CD000", "일반"),
    CD001("CD001", "아픔"),
    CD002("CD002","수면"),
    CD004("CD004","배고픔"),
    CD007("CD007","진화대기")
}