package com.application.microservice.item.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.application.microservice.item.models.Item;
import com.application.microservice.item.models.Product;

@Service("service")
public class ItemServiceImpl implements IItemService {

	@Autowired
	private RestTemplate clientRest;
	
	@Override
	public List<Item> findAll() {
		return Arrays.asList(clientRest.getForObject("http://localhost:8001/list", Product[].class)).stream()
				.map(p -> new Item(p, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Product producto = clientRest.getForObject("http://localhost:8001/show/{id}", Product.class, pathVariables);
		
		return new Item(producto, quantity);
	}
}
