package com.paymong.auth.global.code;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum AuthStateCode {
    ADMIN("AU100", "ADMIN"),
    USER("AU101", "USER");

    private final String code;
    private final String name;

}
