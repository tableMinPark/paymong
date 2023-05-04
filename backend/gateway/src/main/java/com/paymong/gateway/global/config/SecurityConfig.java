//package com.paymong.gateway.global.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Configuration
//@EnableWebFluxSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//            .cors().and()
//            .authorizeExchange((exchanges) -> exchanges
//                .pathMatchers("/auth/login","/auth/register").permitAll()
//                .anyExchange().authenticated())
//            .formLogin().disable()
//            .csrf().disable()
//            .cors().and()
//            .exceptionHandling()
//            .authenticationEntryPoint((swe, e) -> Mono
//                .fromRunnable(() -> swe.getResponse().setStatusCode(
//                    HttpStatus.UNAUTHORIZED)))
//            .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe
//                .getResponse().setStatusCode(HttpStatus.FORBIDDEN)));
//       http.addFilterBefore()
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
