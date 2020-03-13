package com.dnd5e.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.hero.LifeStyle;
import com.dnd5e.wiki.model.treasure.Rarity;

@Controller
@RequestMapping("/calc")
public class CalculatorController {
	@GetMapping("/idle")
	public String getIdleForm(Model model) {
		model.addAttribute("lifeStyles", LifeStyle.values());
		model.addAttribute("costs", new float[] {0, 0.1f, 0.2f, 1, 2, 4, 10 });
		return "idle";
	}

	@GetMapping("/buyMagicThings")
	public String getBuyMagicThingsForm(Model model) {
		model.addAttribute("costs", new float[] {0, 0.1f, 0.2f, 1, 2, 4, 10 });
		model.addAttribute("rarities", Rarity.values());
		return "buyMagicThings";
	}
}