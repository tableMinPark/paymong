//
//package com.paymong.gateway.global.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class TokenProvider {
//
//    @Value("${jwt.secret}")
//    private String JWT_KEY;
//
//    public Claims extractAllClaims(String token) { // 2
//        return Jwts.parserBuilder()
//            .setSigningKey(getSigningKey(JWT_KEY))
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
//    }
//
//    public String getUsername(String token) {
//        return extractAllClaims(token).get("username", String.class);
//    }
//
//    private Key getSigningKey(String secretKey) {
//        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public Boolean isTokenExpired(String token) {
//        Date expiration = extractAllClaims(token).getExpiration();
//        return expiration.before(new Date());
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        String username = getUsername(token);
//        return username.equals(userDetails.getUsername())
//            && !isTokenExpired(token);
//    }
//
//    public long getRemainMilliSeconds(String token) {
//        Date expiration = extractAllClaims(token).getExpiration();
//        Date now = new Date();
//        return expiration.getTime() - now.getTime();
//    }
//}