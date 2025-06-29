package com.paymentchain.setups;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
//@Slf4j
public class GlobalPostFiltering {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalPostFiltering.class);

    @Bean
    public GlobalFilter postGlobalFilter(){
        return ((exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        log.info("global postfilter executed");
                    }));
        });
    }
}
