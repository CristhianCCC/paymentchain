package com.paymentchain.setups;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Slf4j
@Component
public class GlobalPrefiltering implements GlobalFilter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalPrefiltering.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global prefilter executed");
        return chain.filter(exchange);
    }
}
