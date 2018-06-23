package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.creature.Ability;
import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.model.creature.Dice;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.SavingThrow;
import com.dnd5e.wiki.model.creature.Skill;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.repository.CreatureRepository;
import com.dnd5e.wiki.repository.LanguagesRepository;

@Controller
@RequestMapping({ "/creatures" })
public class CreatureController {
	private CreatureRepository repository;
	private LanguagesRepository languagesRepository;
	
	@Autowired
	public void setMonsterRepository(CreatureRepository repo) {
		this.repository = repo;
	}
	
	@Autowired
	public void setLanguagesRepository(LanguagesRepository languagesRepository) {
		this.languagesRepository = languagesRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getСкуфегкуы(Model model) {
		model.addAttribute("creatures", repository.findAll());
		return "creatures";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String getMonsterForm(Model model) {
		model.addAttribute("monster", new Creature());
		return "parseMonster";
	}

	@RequestMapping(value = { "/creature/{id}" }, method = RequestMethod.GET)
	public String getCreature(Model model, @PathVariable Integer id) {
		Creature creature = repository.findById(id).get();
		model.addAttribute("creature", creature);
		return "creatureView";
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

			// размер
			String size = sizeType[0];
			monster.setSize(CreatureSize.parse(size.trim()));
			// тип
			String type = sizeType[1];
			monster.setType(CreatureType.parse(type.trim()));
			if (sizeTypeAligment.length > 1) {
				monster.setAlignment(Alignment.parse(sizeTypeAligment[1].trim()));
			}
			// доспех
			String armorString = reader.readLine();
			if (armorString.contains("Класс Доспеха")) {
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
				monster.setCountDiceHp(Short.parseShort(dices[0].trim()));
				dices[1] = dices[1].replace(")", "");
				int dice = Short.parseShort(dices[1].trim());
				monster.setDiceHp(Dice.parse(dice));
				if (hits.length > 3) {
					hits[3] = hits[3].replaceAll("[^0-9]", "").trim();
					if (!hits[3].isEmpty()) {
						monster.setBonusHP(Short.parseShort(hits[3]));
					} else if (hits.length > 4) {
						hits[4] = hits[4].replaceAll("[^0-9]", "").trim();
						monster.setBonusHP(Short.parseShort(hits[4]));
					}
				}
			}

			// Скорость
			String speedString = reader.readLine();
			if (speedString.contains("Скорость")) {
				String[] speeds = speedString.split(",");
				speeds[0] = speeds[0].replaceAll("[^0-9]", "");
				monster.setSpeed(Short.parseShort(speeds[0].trim()));
				for (int i = 1; i < speeds.length; i++) {
					String otherSpeed = speeds[i];
					if (otherSpeed.contains("летая")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]", "").trim();
						monster.setFlySpeed(Short.parseShort(otherSpeed));
					} else if (otherSpeed.contains("плавая")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]", "").trim();
						monster.setSwimmingSpped(Short.parseShort(otherSpeed));
					} else if (otherSpeed.contains("лазая")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]", "").trim();
						monster.setClimbingSpeed(Short.parseShort(otherSpeed));
					}
				}
			}
			reader.readLine();
			String abilitesString = reader.readLine();
			abilitesString = abilitesString.replace("–", "+");
			abilitesString = abilitesString.replaceAll("\\( \\+[0-9] \\)", "");
			String[] abilites = abilitesString.split("\\s+");
			monster.setStrength(Byte.valueOf(abilites[0]));
			monster.setDexterity(Byte.valueOf(abilites[1]));
			monster.setConstitution(Byte.valueOf(abilites[2]));
			monster.setIntellect(Byte.valueOf(abilites[3]));
			monster.setWizdom(Byte.valueOf(abilites[4]));
			monster.setCharisma(Byte.valueOf(abilites[5]));

			List<SavingThrow> savingThrows = new ArrayList<>();
			List<Skill> skillsList = new ArrayList<>();
			// навыки
			String part = null;
			do {
				part = reader.readLine();
				if (part.startsWith("Спасброски")) {
					part = part.replace("Спасброски ", "");
					String[] savingThrowsPair = part.split(",");
					for (String string : savingThrowsPair) {
						String[] parts = string.trim().split(" ");
						SavingThrow savingThrow = new SavingThrow();
						savingThrow.setAbility(Ability.parseShortName(parts[0].trim()));
						savingThrow.setBonus(Byte.parseByte(parts[1].trim()));
						savingThrows.add(savingThrow);
					}
				} else if (part.startsWith("Навыки")) {
					part = part.replace("Навыки ", "");
					String[] skills = part.split(",");
					for (String string : skills) {
						Skill skill = new Skill();
						String[] skillEl = string.trim().split(" ");
						skill.setType(SkillType.parse(skillEl[0].trim()));
						skill.setBonus(Byte.parseByte(skillEl[1].trim()));
						skillsList.add(skill);
					}
					if (part.endsWith(",")) {
						part = reader.readLine();
						skills = part.split(",");
						for (String string : skills) {
							Skill skill = new Skill();
							String[] skillEl = string.trim().split(" ");
							skill.setType(SkillType.parse(skillEl[0].trim()));
							skill.setBonus(Byte.parseByte(skillEl[1].trim()));
							skillsList.add(skill);
						}
					}
				} else if (part.startsWith("Иммунитет к урону")) {

				} else if (part.startsWith("Сопротивление к урону")) {

				} else if (part.startsWith("Уязвимость к урону")) {

				} else if (part.startsWith("Иммунитет к состоянию")) {

				} else if (part.startsWith("Чувства")) {

				}
			} while (!part.startsWith("Чувства"));
			monster.setSavingThrows(savingThrows);
			monster.setSkills(skillsList);
			
			//чувства

			//языки
			part = reader.readLine();
			if (part.startsWith("Языки")) {
				List<Language> languageList = new ArrayList<>();
				part = part.replace("Языки ", "");
				String[] languages = part.split(",");
				for (String lang : languages) {
					Language language = languagesRepository.findByName(lang.trim());
					if (language == null) {
						language = new Language();
						language.setName(lang.trim());
						language = languagesRepository.save(language);
					}
					languageList.add(language);
				}
				monster.setLanguages(languageList);
			}
			
			// Опасность
			part = reader.readLine();
			part = part.replace("Опасность ", "").trim();
			String[] crAndExp = part.split(" ");
			monster.setChallengeRating(crAndExp[0].trim());
			monster.setExp(Integer.valueOf(crAndExp[1].replaceAll("[^0-9]", "").trim()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		repository.save(monster);
		return "parseMonster";
	}
}