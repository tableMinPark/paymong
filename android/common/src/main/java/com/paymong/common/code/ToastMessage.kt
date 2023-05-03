package com.paymong.common.code

enum class ToastMessage(
    val message: String,
) {
    PERMISSION_DENIED("신체 활동 및 위치 권한이 없습니다.\n권한에 동의해주세요.\n(애플리케이션 - 권한 - 페이몽)"),

    BATTLE_NOT_MATCHING("근처에 매칭 상대가 없습니다."),

    LOGIN_ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다."),
    LOGIN_FAIL("로그인에 실패했습니다. 잠시후 다시 시도 해주세요.")
}
