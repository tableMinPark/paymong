package com.paymong.gateway.global.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    // 설정 정보를 제공하는 클래스
    @Data
    public static class Config {

        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }


    public GlobalFilter() {
        super(Config.class);
    }

    // 필터의 동작을 정의한 메서드
    @Override
    public GatewayFilter apply(Config config) {
        // custom pre filter
        return (exchange, chain) -> {
            // 요청이 전달되었을 때 요청 아이디를 로그로 출력
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage = {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Global Filter is start ... request id = {}", request.getId());
            }

            // custom post filter
            // 응답의 처리상태코드를 로그로 출력
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Global Filter is end ... status code = {}", response.getStatusCode());
                }
            }));
        };
    }

}