package com.application.microservice.product.controllers;

import java.util.List;

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
	public ProductModel details(@PathVariable Long id) {
		return productoService.findById(id);
	}
}
