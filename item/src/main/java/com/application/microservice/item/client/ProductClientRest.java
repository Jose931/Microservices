package com.application.microservice.item.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.application.microservice.item.models.Product;

@FeignClient(name = "product.service")
public interface ProductClientRest {
	
	@GetMapping("/list")
	public List<Product> list();
	
	@GetMapping("/show/{id}")
	public Product details(@PathVariable Long id);
}
