package com.application.microservice.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.microservice.product.dao.ProductDao;
import com.application.microservice.product.models.ProductModel;

@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	private ProductDao productDao;
	
	@Override
	public List<ProductModel> findAll() {
		return (List<ProductModel>) productDao.findAll();
	}

	@Override
	public ProductModel findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

}
