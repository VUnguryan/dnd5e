package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.model.stock.Equipment;
import com.dnd5e.wiki.repository.EquipmentRepository;

@RestController

public class EquipmentRestController {
	@Autowired
	private EquipmentRepository equipmentRepo;
	
	@GetMapping("/equipments/json")
	public List<JsonDataModel> getEquipment(String q) {
		List<JsonDataModel> models = new ArrayList<>();
		for (Equipment equipment : equipmentRepo.findByNameContaining(q)) {
			models.add(new JsonDataModel(equipment.getId(), equipment.getName()));
		}
		return models;
	}
}
