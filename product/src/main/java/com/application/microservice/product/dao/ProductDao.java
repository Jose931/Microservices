package com.application.microservice.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.microservice.product.models.ProductModel;

public interface ProductDao extends JpaRepository<ProductModel, Long>{

}
