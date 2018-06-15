package com.dnd5e.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.repository.CreatureRepository;

@Controller
@RequestMapping({ "/monsters" })
public class CreatureController {
	private CreatureRepository repository;

	@Autowired
	public void setMonsterRepository(CreatureRepository repo) {
		this.repository = repo;
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET )
	public String getMonsterForm(Model model) {
		model.addAttribute("monster", new Creature());
		return "addMonster";
	}

	@RequestMapping(value = { "/add" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String getMonsterForm(Model model, Creature monster) {
		model.addAttribute("monster", new Creature());
		this.repository.save(monster);
		return "addMonster";
	}
}