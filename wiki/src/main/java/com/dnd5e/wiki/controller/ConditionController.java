package com.dnd5e.wiki.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dnd5e.wiki.model.hero.Condition;

@Controller
public class ConditionController {
	
	@GetMapping("/conditions/{condition:\\d+}")
	public String getSpell(Model model, @PathVariable Condition condition) {
		model.addAttribute("condition", ResponseEntity.ok(condition).getBody());
		return "conditionView";
	}
}