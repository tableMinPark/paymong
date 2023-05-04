package com.paymong.auth.global.security;

import com.paymong.auth.auth.dto.response.SecureResDto;
import com.paymong.auth.auth.entity.Auth;
import com.paymong.auth.auth.entity.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetail implements UserDetails {

    private String password;    // 비밀번호
    private String email;    //이메일

    private List<String> roles = new ArrayList<>();    //권한 목록

    public static UserDetails of(SecureResDto member) {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return CustomUserDetail.builder()
            .email(member.getPlayerId())
            .password(member.getPassword())
            .roles(roles)
            .build();
    }

    /**
     * 해당 유저의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    /**
     * 비밀번호
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 이메일
     */
    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return false;
    }


}
