package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.stock.Armor;
import com.dnd5e.wiki.model.stock.ArmorType;
import com.dnd5e.wiki.repository.ArmorRepository;

@Controller
@RequestMapping("/stock/armors")
public class ArmorController {
	
	@Autowired
	private ArmorRepository repo;
	
	@GetMapping
	public String getArmors(Model model) {
		Map<ArmorType, List<Armor>> types = repo.findAll().stream().collect(Collectors.groupingBy(Armor::getType));
		model.addAttribute("armors", types);
		model.addAttribute("types", ArmorType.values());
		return "/equipment/armors";
	}
}