package com.application.microservice.gateway.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ExampleGatewayFilterFactory
		extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuration> {
	
	

	public ExampleGatewayFilterFactory() {
		super(Configuration.class);
	}

	private final Logger logger = LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);

	public static class Configuration {
		private String message;
		private String cookieValue;
		private String cookieName;
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCookieValue() {
			return cookieValue;
		}
		public void setCookieValue(String cookieValue) {
			this.cookieValue = cookieValue;
		}
		public String getCookieName() {
			return cookieName;
		}
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}
		
		
	}

	@Override
	public GatewayFilter apply(Configuration config) {
		return (exchange, chain) -> {
			logger.info("Executing pre gateway filter factory: " + config.message);
			
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				Optional.ofNullable(config.cookieValue).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, config.cookieValue).build());
				});
				
				logger.info("Executing post gateway filter factory: " + config.message);
			}));
		};
	}

}
