package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping({ "/stock/equipments" })
public class EquipmentController {

	private EquipmentRepository equipmentRepository;

	@Autowired
	public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
		this.equipmentRepository = equipmentRepository;
	}

	@GetMapping
	public String getAllEquipments(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		model.addAttribute("currencies", Currency.values());
		model.addAttribute("equipments", equipmentRepository.findAll(page));
		return "/hero/equipments";
	}

	@GetMapping(params = "search")
	public String searchEquipments(Model model,
			@PageableDefault(size = 12, sort = "name") Pageable page,
			String search) {
		model.addAttribute("currencies", Currency.values());
		model.addAttribute("equipments", equipmentRepository.findByNameContaining(page, search));
		return "/hero/equipments";
	}

	@GetMapping(params = "currencyType")
	public String getEquipmentsForCurrencyType(Model model, @PageableDefault(size = 12, sort = "name") Pageable page,
			String currencyType) {
		if ("ALL".equals(currencyType)) {
			return "redirect:/equipments";
		}
		Currency selectedCurrency = Currency.valueOf(currencyType);
		model.addAttribute("currencySelected", selectedCurrency);
		model.addAttribute("currencies", Currency.values());
		Page<Equipment> equipments = equipmentRepository.findAll(page);
		equipments.forEach(e -> convertor(e, selectedCurrency));
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
		equipment.setCost((int) selectedCurrency.convert(currency, equipment.getCost()));
		equipment.setCurrency(selectedCurrency);
		return equipment;
	}
}