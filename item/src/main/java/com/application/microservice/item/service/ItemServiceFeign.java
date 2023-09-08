package com.application.microservice.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.microservice.item.client.ProductClientRest;
import com.application.microservice.item.models.Item;

@Service("serviceFeign")
public class ItemServiceFeign implements IItemService {
	
	@Autowired
	private ProductClientRest clienteFeign;

	@Override
	public List<Item> findAll() {
		return clienteFeign.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		return new Item(clienteFeign.details(id), quantity);
	}

}
