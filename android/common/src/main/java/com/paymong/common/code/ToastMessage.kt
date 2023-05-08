package com.paymong.common.code

enum class ToastMessage(
    val message: String,
) {


    // 워치 페이몽 설치여부
    WEARABLE_INSTALL_REQUEST("현재 연결된 Wearable 기기에 설치되지 않았습니다"),
    WEARABLE_INSTALL_SUCCESS("Wearable 기기에 설치 완료"),
    MOBILE_INSTALL_REQUEST("현재 연결된 Mobile 기기에 설치되지 않았습니다"),
    MOBILE_INSTALL_SUCCESS("Mobile 기기에 설치 완료"),
    INSTALL_FAIL("페이몽이 기기에 지원되지 않아 설치할 수 없습니다."),
    VALID_FAIL("지원되지 않는 기기입니다."),

    // 권한
    PERMISSION_DENIED("신체 활동 및 위치 권한이 없습니다.\n권한에 동의해주세요.\n(애플리케이션 - 권한 - 페이몽)"),
    
    // 배틀 매칭
    BATTLE_NOT_MATCHING("근처에 매칭 상대가 없습니다."),

    // 로그인
    LOGIN_ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다."),
    LOGIN_FAIL("로그인에 실패했습니다. 잠시후 다시 시도 해주세요.")
}

