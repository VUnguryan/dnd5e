package com.dnd5e.wiki.controller;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.dto.ArchitypeDto;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.ArchetypeSpellRepository;
import com.dnd5e.wiki.repository.SpellRepository;

@Controller
@RequestMapping({ "/hero/spells" })
public class SpellController {
	@Autowired
	private ArchetypeSpellRepository aSpellRepo; 
	
	@Autowired
	private SpellRepository repo;
	
	@GetMapping
	public String getSpellTable(Model model) {
		model.addAttribute("order", "[[ 1, 'asc' ]]");
		return "datatable/spells";
	}

	@GetMapping("/spell/{spell:\\d+}")
	public String getSpell(Model model, @PathVariable Spell spell) {
		model.addAttribute("spell", ResponseEntity.ok(spell).getBody());
		model.addAttribute("arhitypes", aSpellRepo.findAllBySpellId(spell.getId()).stream().map(ArchitypeDto::new).collect(Collectors.toList()));
		return "spellView";
	}

	//@PostMapping ("convert")
	//@ResponseBody
	public String convertToUrl(String spells) {
		Spell spell = new Spell();
		return Arrays.asList(spells.split(",")).stream()
				.map(String::trim)
				.peek(s -> spell.setEnglishName(s))
				.map(s -> repo.findOne(Example.of(spell)))
				.map(s -> s.orElse(null))
				.filter(Objects::nonNull)
				.map(s -> " <a href=\"/hero/spells/spell/"+s.getId()+"\">" + s.getName().toLowerCase() + " [" + s.getEnglishName().toLowerCase() + "]" + "</a>")
				.collect(Collectors.joining(", "));
	}
}