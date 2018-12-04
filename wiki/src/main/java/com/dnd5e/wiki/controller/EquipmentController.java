package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.hero.Currency;
import com.dnd5e.wiki.model.hero.Equipment;
import com.dnd5e.wiki.repository.EquipmentRepository;

@Controller
@RequestMapping({ "/equipments" })
public class EquipmentController {

	private EquipmentRepository equipmentRepository;

	@Autowired
	public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
		this.equipmentRepository = equipmentRepository;
	}

	@GetMapping
	public String getAllEquipments(Model model) {
		model.addAttribute("currencies", Currency.values());
		model.addAttribute("equipments", equipmentRepository.findAll(new Sort(Sort.Direction.ASC, "name")));
		return "/hero/equipments";
	}

	@GetMapping(params = "search")
	public String searchEquipments(Model model, String search)
	{
		model.addAttribute("currencies", Currency.values());
		model.addAttribute("equipments", equipmentRepository.findByNameContaining(search));
		return "/hero/equipments";
	}
	
	@GetMapping(params = "currencyType")
	public String getEquipmentsForCurrencyType(Model model, String currencyType) {
		if ("ALL".equals(currencyType)) {
			return "redirect:/equipments";
		}
		Currency selectedCurrency = Currency.valueOf(currencyType);
		model.addAttribute("currencySelected", selectedCurrency);
		model.addAttribute("currencies", Currency.values());
		List<Equipment> equipments = equipmentRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
		equipments = equipments.stream().map(e -> convertor(e, selectedCurrency)).collect(Collectors.toList());
		model.addAttribute("equipments", equipments);
		return "/hero/equipments";
	}

	@GetMapping("/add")
	public String getEquipmentForm(Model model) {
		model.addAttribute("equipment", new Equipment());
		model.addAttribute("currencies", Currency.values());
		return "/hero/addEquipment";
	}

	@PostMapping("/add")
	public String addEquipment(@ModelAttribute Equipment equipment) {
		equipmentRepository.save(equipment);
		return "redirect:/equipments/add";
	}

	private Equipment convertor(Equipment equipment, Currency selectedCurrency) {

		Currency currency = equipment.getCurrency();
		if (currency.ordinal() <= selectedCurrency.ordinal()) {
			return equipment;
		}
		equipment.setCost(selectedCurrency.convert(currency, equipment.getCost()));
		equipment.setCurrency(selectedCurrency);
		return equipment;
	}
}
