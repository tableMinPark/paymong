package com.paymong.common.code

enum class ToastMessage(
    val message: String,
) {
    // 권한
    PERMISSION_DENIED("신체 활동 및 위치 권한이 없습니다.\n권한에 동의해주세요.\n(애플리케이션 - 권한 - 페이몽)"),
    // 배틀 매칭
    BATTLE_NOT_POINT("포인트가 부족합니다."),
    BATTLE_NOT_MATCHING("근처에 매칭 상대가 없습니다."),
    // 훈련
    TRAINING_NOT_POINT("포인트가 부족합니다."),
    // 버튼 막기
    BUTTON_NOT_ACTIVE("사용할 수 없는 상태입니다.")
}

