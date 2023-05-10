package com.paymong.common.code

enum class ToastMessage(
    val message: String,
) {
    // 로그인
    LOGIN_ACCOUNT_NOT_FOUND("구글 게임즈 계정을 찾을 수 없습니다."),

    LOGIN_SUCCESS("로그인 성공"),
    REGIST_WEARABLE_SUCCESS("로그인"),
    REGIST_WEARABLE_FAIL("등록되거나 연결 가능한 Wearable 기기가 없습니다."),
    HAS_WEARABLE_SUCCESS("등록된 Wearable 기기가 없습니다."),
    HAS_WEARABLE_FAIL("Wearable 기기에 페이몽이 설치되지 않았습니다."),
    LOGIN_FAIL("로그인에 실패했습니다. 잠시후 다시 시도 해주세요."),

    INSTALL("Mobile 앱에 Wearable 기기 등록이 필요합니다."),
    NOT_INSTALL("Mobile 앱에 페이몽 앱 설치가 필요합니다."),

    // 권한
    PERMISSION_DENIED("신체 활동 및 위치 권한이 없습니다.\n권한에 동의해주세요.\n(애플리케이션 - 권한 - 페이몽)"),
    
    // 배틀 매칭
    BATTLE_NOT_MATCHING("근처에 매칭 상대가 없습니다."),

    // 훈련
    TRAINING_NOT_POINT("포인트가 부족합니다.")

}

