package com.application.microservice.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

	private final Logger logger = LoggerFactory.getLogger(GlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Executing pre filter");
		exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("Executing post filter");
			
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(value -> {
				exchange.getResponse().getHeaders().add("token", value);
			});		
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		return -1;
	}

}
