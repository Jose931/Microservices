package com.application.microservice.item.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.microservice.item.models.Item;
import com.application.microservice.item.models.Product;
import com.application.microservice.item.service.IItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@RestController
public class ItemController {
	
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")
	private IItemService itemService;
	
	@GetMapping("/list")
	public List<Item> list(@RequestParam(name="name", required= false) String name, @RequestHeader(name="token-request", required= false) String token){
		System.out.println(name);
		System.out.println(token);
		return itemService.findAll();
	}
	
	/*
	 * With the CircuitBreaker Bean in AppConfig
	 * */
	@GetMapping("/show/{id}/quantity/{quantity}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer quantity) {
		return cbFactory.create("items")
				.run(() -> itemService.findById(id, quantity), e -> alternativeMethod(id, quantity, e));
	}
	
	/*
	 * With the application.yml configuration
	 * */
	@CircuitBreaker(name="items", fallbackMethod = "alternativeMethod")
	@GetMapping("/show2/{id}/quantity/{quantity}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer quantity) {
		return  itemService.findById(id, quantity);
	}
	
	
	/*
	 * With the application.yml configuration
	 * */
	@CircuitBreaker(name="items", fallbackMethod = "alternativeMethod2")
	@TimeLimiter(name="items")
	@GetMapping("/show3/{id}/quantity/{quantity}")
	public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer quantity) {
		return  CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity));
	}
	
	public Item alternativeMethod(Long id, Integer quantity, Throwable e) {
		logger.info(e.getMessage());
		
		Item item = new Item();
		Product product = new Product();
		
		item.setQuantity(quantity);
		product.setId(id);
		product.setName("Sony Camera");
		product.setPrice(500.00);
		item.setProduct(product);
		return item;
	}

	public CompletableFuture<Item> alternativeMethod2(Long id, Integer quantity, Throwable e) {
		logger.info(e.getMessage());
		
		Item item = new Item();
		Product product = new Product();
		
		item.setQuantity(quantity);
		product.setId(id);
		product.setName("Sony Camera");
		product.setPrice(500.00);
		item.setProduct(product);
		return CompletableFuture.supplyAsync(() -> item);
	}
}
