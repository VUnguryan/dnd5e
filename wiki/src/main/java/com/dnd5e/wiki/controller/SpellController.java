package com.dnd5e.wiki.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.spell.Spell;

@Controller
@RequestMapping({ "/hero/spells" })
public class SpellController {
	@GetMapping
	public String getSpellTable(Model model) {
		model.addAttribute("order", "[[ 1, 'asc' ]]");
		return "datatable/spells";
	}

	@GetMapping("/spell/{spell:\\d+}")
	public String getSpell(Model model, @PathVariable Spell spell) {
		model.addAttribute("spell", ResponseEntity.ok(spell).getBody());
		return "spellView";
	}
}