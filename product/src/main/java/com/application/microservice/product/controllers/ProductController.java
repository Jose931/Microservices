package com.application.microservice.product.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.application.microservice.product.models.ProductModel;
import com.application.microservice.product.service.IProductService;

@RestController
public class ProductController {
	
	@Autowired
	private IProductService productoService;
	
	@GetMapping("/list")
	public List<ProductModel> list(){
		return productoService.findAll();
	}
	
	@GetMapping("/show/{id}")
	public ProductModel details(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)) {
			throw new IllegalStateException("The product does not exist");
		}
		
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		return productoService.findById(id);
	}
}
