package com.paymong.common.code

enum class AnimationCode(
    val code: String,
    val codeName: String,
) {
    Normal("AN100", "일반"),
    Sleep("AN101", "잠자기"),
    Feed("AN102", "밥먹기"),
    Poop("AN103", "똥치우기")
}