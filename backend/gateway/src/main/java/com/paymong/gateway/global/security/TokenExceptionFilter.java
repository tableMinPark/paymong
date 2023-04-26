//package com.paymong.gateway.global.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.JwtException;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TokenExceptionFilter implements WebFilter {
//
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange serverWebExchange,
//        WebFilterChain webFilterChain) {
//        try {
//
//        } catch (JwtException ex) {
//
//        }
//        return webFilterChain.filter(serverWebExchange);
//    }
//
//        public Mono<ServerResponse> setErrorResponse (HttpStatus status, ServerWebExchange serverWebExchange, Throwable ex)
//        throws IOException {
//            // 토큰이 없을때 터짐
//            serverWebExchange.getResponse().setRawStatusCode(status.value());
//            serverWebExchange.getResponse().getHeaders()
//                .setContentType(new MediaType("application/json; charset=UTF-8"));
//            ServerHttpResponse serverHttpResponse;
////            response.getWriter().write(objectMapper.writeValueAsString("토큰이 만료되었습니다."));
//        }
//    }
