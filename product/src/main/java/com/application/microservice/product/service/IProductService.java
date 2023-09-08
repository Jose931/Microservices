package com.application.microservice.product.service;

import java.util.List;

import com.application.microservice.product.models.ProductModel;

public interface IProductService {
	
	public List<ProductModel> findAll();
	
	public ProductModel findById(Long id);
}
