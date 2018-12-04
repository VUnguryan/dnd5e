package com.dnd5e.wiki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.hero.Condition;
import com.dnd5e.wiki.repository.ConditionRepository;

@Controller
public class HeroController {
	private ConditionRepository conditionRepositoryl;

	@Autowired
	public void setConditionRepositoryl(ConditionRepository conditionRepositoryl) {
		this.conditionRepositoryl = conditionRepositoryl;
	}

	@GetMapping("/conditions")
	public String getCondinons(Model model) {
		model.addAttribute("conditions", conditionRepositoryl.findAll());
		return "/hero/conditions";
	}

	@RequestMapping(value = "conditions", params = { "search" })
	public String searchConditions(Model model, String search) {
		List<Condition> conditions = conditionRepositoryl.findAll(byName(search));
		model.addAttribute("conditions", conditions);
		return "/hero/conditions";
	}

	private Specification<Condition> byName(String search) {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("englishName"), "%" + search + "%"));
	}
}