package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.SpellRepository;

@Controller
@RequestMapping({ "/hero/spells" })
public class SpellController {
	private SpellRepository spellRepository;

	@Autowired
	public void setSpellRepository(SpellRepository spellRepository) {
		this.spellRepository = spellRepository;
	}
	
	@GetMapping
	public String getSpellTable(Model model) {
		model.addAttribute("order", "[[ 1, 'asc' ]]");
		return "datatable/spells";
	}

	@GetMapping("/spell/{id}")
	public String getSpell(Model model, @PathVariable Integer id) {
		Spell spell = spellRepository.findById(id).get();
		model.addAttribute("spell", spell);
		return "spellView";
	}
}