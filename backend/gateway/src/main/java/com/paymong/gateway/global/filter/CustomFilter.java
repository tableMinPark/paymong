package com.paymong.gateway.global.filter;

import com.paymong.gateway.global.redis.RefreshToken;
import com.paymong.gateway.global.redis.RefreshTokenRedisRepository;
import com.paymong.gateway.global.security.TokenProvider;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    private final TokenProvider tokenProvider;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    // 설정 정보를 제공하는 클래스
    public static class Config {
        // 설정 정보가 필요한 경우 명시
    }

    public CustomFilter(TokenProvider tokenProvider,
        RefreshTokenRedisRepository refreshTokenRedisRepository) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
    }

    // 필터의 동작을 정의한 메서드
    @Override
    public GatewayFilter apply(Config config) {
        log.info("apply");
        // custom pre filter
        return (exchange, chain) -> {
            // 요청이 전달되었을 때 요청 아이디를 로그로 출력
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("Custom PRE FILTER: request id = {}", request.getId());


            // 토큰 없을 때
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // 토큰이 있지만 내용이 없거나 jwt가 아닐때
            String token = getToken(request);
            if (token == null) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // 토큰이 유효하지 않을 때
            // redis 에서 확인
            String userName = tokenProvider.getUsername(token);


            Optional<RefreshToken> refreshToken = refreshTokenRedisRepository.findById(userName);

            if (refreshToken.isEmpty()) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            String memberKey = refreshToken.get().getId();

            exchange.mutate().request((exchange.getRequest().mutate().header("MeberKey",memberKey).build()));

            // custom post filter
            // 응답의 처리상태코드를 로그로 출력
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom POST FILTER: response code = {}", response.getStatusCode());
            }));
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error("onError");
        return response.setComplete();
    }

    private String getToken(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        log.info("getToken");
        log.info("headerAuth - "+ headerAuth);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}