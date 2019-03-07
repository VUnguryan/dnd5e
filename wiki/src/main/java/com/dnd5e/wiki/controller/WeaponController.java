package com.dnd5e.wiki.controller;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.Dice;
import com.dnd5e.wiki.model.hero.Currency;
import com.dnd5e.wiki.model.hero.Weapon;
import com.dnd5e.wiki.model.hero.WeaponProperty;
import com.dnd5e.wiki.model.hero.WeaponType;
import com.dnd5e.wiki.repository.WeaponPropertyRepository;
import com.dnd5e.wiki.repository.WeaponRepository;

@Controller
@RequestMapping("/weapons")
public class WeaponController {
	@Autowired
	private WeaponRepository repo;
	@Autowired
	private WeaponPropertyRepository propertyRepo;

	@GetMapping
	public String getWeapons(Model model) {
		Map<WeaponType, List<Weapon>> weapons = repo.findAll().stream().collect(Collectors.groupingBy(Weapon::getType));
		model.addAttribute("weapons", weapons);
		model.addAttribute("types", WeaponType.values());
		model.addAttribute("currencies", Currency.values());
		return "/hero/weapons";
	}
	@GetMapping("/property/{id}")
	public String getPropertyForm(Model model,  @PathVariable Integer id) {
		model.addAttribute("property", propertyRepo.findById(id).orElseGet(WeaponProperty::new));
		return "/hero/weaponProperty";
	}

	@GetMapping("/add")
	public String getForm(Model model) {
		model.addAttribute("weapon", new Weapon());
		model.addAttribute("damageTypes", EnumSet.of(DamageType.PIERCING, DamageType.CRUSHING, DamageType.CHOPPING));
		model.addAttribute("dices", Dice.values());
		model.addAttribute("types", WeaponType.values());
		model.addAttribute("currencies", Currency.values());
		return "/hero/addWeapon";
	}

	@PostMapping("/add")
	public String addWeapon(Weapon weapon) {
		repo.save(weapon);
		return "redirect:/weapons/add";
	}

	@GetMapping("/property/add")
	public String getPropertyForm(Model model) {
		model.addAttribute("weaponProperty", new WeaponProperty());
		return "/hero/addWeaponProperty";
	}

	@PostMapping("/property/add")
	public String addWeaponProperty(WeaponProperty property) {
		propertyRepo.save(property);
		return "redirect:/weapons/property/add";
	}
}