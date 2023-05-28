package com.paymong.common.code

enum class LandingCode {
    // LOADING -> LOGIN_SUCCESS or LOGIN_FAIL

    // LOADING : 로그인 준비
    // SUCCESS : 로그인, 소켓 성공
    // INSTALL : 앱 설치 됨
    // NOT_INSTALL : 앱 설치가 안됨

    LOADING,
    NOT_CONFIG,
    NOT_INSTALL,
    NOT_HAS_WEARABLE,
    LOGIN_FAIL,
    SUCCESS,
    FAIL
}