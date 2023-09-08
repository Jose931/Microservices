package com.application.microservice.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.application.microservice.item.models.Item;
import com.application.microservice.item.service.IItemService;

@RestController
public class ItemController {
	
	@Autowired
	@Qualifier("serviceFeign")
	private IItemService itemService;
	
	@GetMapping("/list")
	public List<Item> list(){
		return itemService.findAll();
	}
	
	@GetMapping("/show/{id}/quantity/{quantity}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer quantity) {
		return itemService.findById(id, quantity);
	}

}
