package com.dnd5e.wiki.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.treasure.MagicThing;

@Controller
@RequestMapping("/stock")
public class TreasureController {
	@GetMapping("/artifacts")
	public String getMagicThings() {
		return "datatable/magicThings";
	}
	
	@GetMapping("/artifacts/{magicThing}")
	public String getMagicThing(Model model, @PathVariable MagicThing magicThing) {
		model.addAttribute("magicThing", ResponseEntity.ok(magicThing).getBody());
		return "equipment/magicalThingView";
	}
	
	@GetMapping("/treasures")
	public String getTreasures() {
		return "datatable/treasures";
	}
}