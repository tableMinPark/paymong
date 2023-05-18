package com.paymong.auth.global.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    private final CustomUserDetailService customUserDetailService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String accessToken = getToken((HttpServletRequest) request);

        // accessToken 이 있고 redis 에 accessToken 이 존재하지 않을 때 = 로그아웃 상태
        if (accessToken != null) {
            try {
                // 토큰의 정보를 이용해 사용자 정보 생성
                String username = tokenProvider.getUsername(accessToken);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

                // 사용자 정보와 토큰을 이용해 토큰 검증
                if (tokenProvider.validateToken(accessToken, userDetails)) {
                    // 이상이 없으면 인가 객체 생성해서 시큐리티 컨텍스트 공간에 저장
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (IllegalArgumentException | ExpiredJwtException e) {
                throw new JwtException(e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
