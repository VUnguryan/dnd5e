package com.dnd5e.wiki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.repository.SpellRepository;

@Controller
@RequestMapping({ "/spells" })
public class SpellController {
	private SpellRepository spellRepository;
	private ClassRepository classRepository;

	public SpellController() {
	}

	@Autowired
	public void setSpellRepository(SpellRepository spellRepository) {
		this.spellRepository = spellRepository;
	}

	@Autowired
	public void setClassRepository(ClassRepository classRepository) {
		this.classRepository = classRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getSpells(Model model) {
		model.addAttribute("order", Integer.valueOf(1));
		model.addAttribute("spells", spellRepository.findAll());
		return "spells";
	}

	@RequestMapping(method = RequestMethod.GET, params = "order")
	public String sortSpells(Model model, Integer order, String dir) {
		Sort sort = null;
		Sort.Direction direction = null;
		if (("asc".equals(dir)) || (dir == null)) {
			direction = Sort.Direction.ASC;
		} else {
			direction = Sort.Direction.DESC;
		}
		switch (order.intValue()) {
		case 0:
			sort = new Sort(direction, "level");
			break;
		case 1:
			sort = new Sort(direction, "name");
			break;
		case 2:
			sort = new Sort(direction, "school");
			break;
		default:
			sort = Sort.unsorted();
		}

		model.addAttribute("spells", spellRepository.findAll(sort));
		model.addAttribute("order", order);
		model.addAttribute("dir", dir);
		return "spells";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "search" })
	public String searchSpells(Model model, String search) {
		List<Spell> spells = spellRepository.findByNameAndEnglishNameContaining(search);
		if (spells.size() == 1) {
			return "forward:spells/spell/" + spells.get(0).getId();
		}
		model.addAttribute("spells", spells);
		return "spells";
	}

	@RequestMapping(value = { "/add" }, method = { RequestMethod.GET })
	public String getSpellAddForm(Model model) {
		return "addSpell";
	}

	@RequestMapping(value = { "/spell/{id}" }, method = RequestMethod.GET)
	public String getSpell(Model model, @PathVariable Integer id) {
		Spell spell = spellRepository.findById(id).get();
		model.addAttribute("spell", spell);
		model.addAttribute("classes", classRepository.findBySpellName(spell.getName()));
		return "spellView";
	}
}