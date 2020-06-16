package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.repository.EquipmentRepository;

@Controller
@RequestMapping({ "/stock/equipments" })
public class EquipmentController {

	private EquipmentRepository equipmentRepository;

	@GetMapping
	public String getAllEquipments()
	{
		return "datatable/equipments";
	}
}