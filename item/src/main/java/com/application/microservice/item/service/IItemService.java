package com.application.microservice.item.service;

import java.util.List;

import com.application.microservice.item.models.Item;

public interface IItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer quantity);
}
