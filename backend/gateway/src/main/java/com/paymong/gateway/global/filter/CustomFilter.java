package com.paymong.gateway.global.filter;

import com.paymong.gateway.global.entity.Mong;
import com.paymong.gateway.global.redis.RefreshToken;
import com.paymong.gateway.global.redis.RefreshTokenRedisRepository;
import com.paymong.gateway.global.repository.MongRepository;
import com.paymong.gateway.global.security.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
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

    private final MongRepository mongRepository;

    // 설정 정보를 제공하는 클래스
    public static class Config {
        // 설정 정보가 필요한 경우 명시
    }

    public CustomFilter(TokenProvider tokenProvider,
        RefreshTokenRedisRepository refreshTokenRedisRepository, MongRepository mongRepository) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
        this.mongRepository = mongRepository;
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

            Optional<RefreshToken> refreshToken;
            try{
                String userName = tokenProvider.getUsername(token);
                refreshToken = refreshTokenRedisRepository.findById(userName);
            }catch(ExpiredJwtException e){
                log.info("Custom POST FILTER: response code = {} message = {}",
                    HttpStatus.FORBIDDEN.value(), "토큰 만료");
                return onError(exchange, "토큰 만료", HttpStatus.FORBIDDEN);
            }


            // userName으로 된 토큰이 없을 때 -> 만료돼서 redis에서 사라짐
            if (refreshToken.isEmpty()) {
                log.info("Custom POST FILTER: response code = {} message = {}",
                    HttpStatus.FORBIDDEN.value(), "토큰 만료");
                return onError(exchange, "토큰 만료", HttpStatus.FORBIDDEN);
            }

            // 토큰이 일치하지 않으면
            if (!refreshToken.get().getAccessToken().equals(token)) {
                log.info("Custom POST FILTER: response code = {} message = {}",
                    HttpStatus.UNAUTHORIZED.value(), "토큰 불일치");
                return onError(exchange, "토큰 불일치", HttpStatus.UNAUTHORIZED);
            }

//             Header 에 memberId 추가
            String memberId = refreshToken.get().getMemberKey();
//            String memberId = "23";
            Mong mong = mongRepository.findByMemberIdAndActive(Long.parseLong(memberId), 1).orElse(
                Mong.builder().build());

            String mongId = String.valueOf(mong.getMongId());

            log.info("memberId = {}", memberId);
            log.info("mongId = {}", mongId);

            if (mongId.equals("null")) {
                mongId = "";
            }

            exchange.getRequest().mutate().header("MemberId", memberId).build();
            exchange.getRequest().mutate().header("MongId", mongId).build();
            log.info("request path - {}", request.getPath());
            log.info("request uri - {}", request.getURI());

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
        log.info("headerAuth - " + headerAuth);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}