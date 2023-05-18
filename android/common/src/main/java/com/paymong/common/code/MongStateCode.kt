package com.paymong.common.code

enum class MongStateCode(
    val state: String,
) {
    CD000("정상"),
    CD001("아픔"),
    CD002("수면"),
    CD003("졸림"),
    CD004("배고픔"),
    CD005("죽음"),
    CD006("졸업"),
    CD007("진화대기"),
    CD008("먹는중")
}