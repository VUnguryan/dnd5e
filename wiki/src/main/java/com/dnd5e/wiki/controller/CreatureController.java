package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

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

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String getMonsterForm(Model model) {
		model.addAttribute("monster", new Creature());
		return "parseMonster";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String getMonsterForm(Model model, Creature monster, String description) {
		model.addAttribute("monster", new Creature());
		
		try (LineNumberReader reader = new LineNumberReader(new StringReader(description))) {
			String name = reader.readLine();
			monster.setName(name.trim());
			String sizeType = reader.readLine();
			String[] sizeTypeAligment = sizeType.split(",");
			String[] size = sizeTypeAligment[0].split("\\s");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this.repository.save(monster);
		return "parseMonster";
	}
}