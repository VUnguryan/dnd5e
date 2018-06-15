package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.creature.Dice;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.repository.CreatureRepository;

@Controller
@RequestMapping({ "/creatures" })
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
				String[] dices = hits[2].split("к");
				monster.setCountHpBone(Short.parseShort(dices[0].trim()));
				int dice= Short.parseShort(dices[0].trim());
				monster.setHpBone(Dice.parse(dice));
				if (hits.length > 2) {
					hits[3] = hits[3].replaceAll("[^0-9]","").trim();
					if(!hits[3].isEmpty()) {
						monster.setBonusHP(Short.parseShort(hits[3]));
					} else if (hits.length>3) {
						hits[4] = hits[4].replaceAll("[^0-9]","").trim();
						monster.setBonusHP(Short.parseShort(hits[4]));
					}
				}
			}
			
			// Скорость
			String speedString = reader.readLine();
			if (speedString.contains("Скорость")) {
				String[] speeds = speedString.split(",");
				speeds[0] = speeds[0].replaceAll("[^0-9]","");
				monster.setSpeed(Short.parseShort(speeds[0].trim()));
				for (int i = 1; i < speeds.length; i++) {
					String otherSpeed = speeds[i];
					if (otherSpeed.contains("летая")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]","").trim();
						monster.setFlySpeed(Short.parseShort(otherSpeed));
					} else if (otherSpeed.contains("плавая")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]","").trim();
						monster.setSwimmingSpped(Short.parseShort(otherSpeed));
					} else if (otherSpeed.contains("лазая")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]","").trim();
						monster.setClimbingSpeed(Short.parseShort(otherSpeed));
					}
				}
			}
			reader.readLine();
			String abilitesString = reader.readLine();
			abilitesString = abilitesString.replace("–", "+");
			abilitesString = abilitesString.replaceAll("\\( \\+[0-9] \\)", "");
			String[] abilites = abilitesString.split("\\s+");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this.repository.save(monster);
		return "parseMonster";
	}
}