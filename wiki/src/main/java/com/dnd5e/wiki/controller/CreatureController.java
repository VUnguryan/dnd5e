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
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
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
			String sizeTypeAligmentString = reader.readLine();
			String[] sizeTypeAligment = sizeTypeAligmentString.split(",");
			String[] sizeType = sizeTypeAligment[0].split("\\s");
			
			String size = sizeType[0];
			monster.setSize(CreatureSize.parse(size.trim()));
			
			String type = sizeType[1];
			monster.setType(CreatureType.parse(type.trim()));
			
			// доспех
			String armorString = reader.readLine();
			if(armorString.contains("Класс Доспеха")) {
				String[] armor = armorString.split(" ");
				byte ac = Byte.parseByte(armor[2].trim());
				monster.setAC(ac);
			}
			// Хиты
			String hitsString = reader.readLine();
			if (hitsString.contains("Хиты")) {
				String[] hits = hitsString.split("\\s");
				monster.setAverageHp(Short.parseShort(hits[1].trim()));
				hits[2] = hits[2].replace("(", "");
				String[] dice = hits[2].split("к");
				monster.setCountHpBone(Short.parseShort(dice[0].trim()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this.repository.save(monster);
		return "parseMonster";
	}
}