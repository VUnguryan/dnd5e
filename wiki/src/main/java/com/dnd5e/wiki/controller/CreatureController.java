package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dnd5e.wiki.model.creature.Ability;
import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureRace;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.Dice;
import com.dnd5e.wiki.model.creature.Feat;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.SavingThrow;
import com.dnd5e.wiki.model.creature.Skill;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.creature.State;
import com.dnd5e.wiki.repository.CreatureRaceRepository;
import com.dnd5e.wiki.repository.CreatureRepository;
import com.dnd5e.wiki.repository.LanguagesRepository;

@Controller
@RequestMapping({ "/creatures" })
public class CreatureController {
	private static final List<String> statusNames = Arrays.asList("Спасброски", "Навыки", "Иммунитет к урону",
			"Сопротивление к урону", "Уязвимость к урону", "Иммунитет к состоянию", "Чувства");

	private CreatureRepository repository;
	private LanguagesRepository languagesRepository;
	private CreatureRaceRepository creatureRaceRepository;

	@Autowired
	public void setMonsterRepository(CreatureRepository repository) {
		this.repository = repository;
	}

	@Autowired
	public void setLanguagesRepository(LanguagesRepository languagesRepository) {
		this.languagesRepository = languagesRepository;
	}

	@Autowired
	public void setCreatureRaceRepository(CreatureRaceRepository creatureRaceRepository) {
		this.creatureRaceRepository = creatureRaceRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getСкуфегкуы(Model model) {
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		model.addAttribute("creatures", repository.findAll(sort));
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
	
	@RequestMapping(method = RequestMethod.GET, params = { "search" })
	public String searchCreature(Model model, String search) {
		model.addAttribute("creatures", repository.findByNameContaining(search));
		return "creatures";
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "order")
	public String sortCreature(Model model, Integer order, String dir) {
		Sort sort = null;
		Sort.Direction direction = null;
		if (("asc".equals(dir)) || (dir == null)) {
			direction = Sort.Direction.ASC;
		} else {
			direction = Sort.Direction.DESC;
		}
		switch (order.intValue()) {
		case 0:
			sort = new Sort(direction, "challengeRating");
			break;
		case 1:
			sort = new Sort(direction, "name");
			break;
		case 2:
			sort = new Sort(direction, "type");
			break;
		default:
			sort = Sort.unsorted();
		}

		model.addAttribute("creatures", repository.findAll(sort));
		model.addAttribute("order", order);
		model.addAttribute("dir", dir);
		return "creatures";
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
			if (sizeType.length > 2) {
				CreatureRace race = creatureRaceRepository.findByName(sizeType[2].trim());
				if (race == null) {
					race = new CreatureRace();
					race.setName(sizeType[2].trim());
					race = creatureRaceRepository.save(race);
				}
				monster.setRaceName(race.getName());
				monster.setRaceId(race.getId());
			}
			// мировозрение
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
			abilitesString = abilitesString.replaceAll("\\(\\s?\\+[0-9]{1,2}\\s?\\)", "");
			String[] abilites = abilitesString.split("\\s+");
			monster.setStrength(Byte.valueOf(abilites[0]));
			monster.setDexterity(Byte.valueOf(abilites[1]));
			monster.setConstitution(Byte.valueOf(abilites[2]));
			monster.setIntellect(Byte.valueOf(abilites[3]));
			monster.setWizdom(Byte.valueOf(abilites[4]));
			monster.setCharisma(Byte.valueOf(abilites[5]));

			// навыки
			String skillText = "";
			String currentSkill = null;
			String token = null;
			do {
				token = reader.readLine();
				boolean startSkill = false;
				for (String status : statusNames) {
					if (token.startsWith(status)) {
						startSkill = true;

						if (!status.equals(currentSkill)) {
							if (currentSkill != null) {
								parseSkill(currentSkill, skillText, monster);
							}
							currentSkill = status;
						}
						skillText = token;
						break;
					}
				}
				if (!startSkill) {
					skillText += " " + token;
				}
			} while (!token.startsWith("Чувства"));
			skillText = token;

			// чувства
			skillText = skillText.replace("Чувства ", "");
			token = reader.readLine();
			if (!token.startsWith("Языки")) {
				skillText += token;
				monster.setVision(skillText);
				skillText = reader.readLine();
			} else {
				monster.setVision(skillText);
				skillText = token;
			}

			// языки
			token = reader.readLine();
			if (!token.startsWith("Опасность")) {
				skillText += " " + token;
				if (skillText.startsWith("Языки")) {
					parseLanguage(skillText, monster);
				}
			} else {
				parseLanguage(skillText, monster);
				skillText = token;
			}

			// Опасность
			if (!skillText.startsWith("Опасность")) {
				skillText = reader.readLine();
			}
			skillText = skillText.replace("Опасность ", "").trim();
			String[] crAndExp = skillText.split(" ");
			monster.setChallengeRating(crAndExp[0].trim());
			monster.setExp(Integer.valueOf(crAndExp[1].replaceAll("[^0-9]", "").trim()));

			// Фиты
			Feat feat = new Feat();
			List<Feat> feats = new ArrayList<>();
			boolean newFeet = true;
			do {
				skillText = reader.readLine();
				if (newFeet) {
					int nameIndex = skillText.indexOf(".");
					if (nameIndex != -1) {
						String nameAction = skillText.substring(0, nameIndex);
						feat.setName(nameAction);
						newFeet = false;
						description = skillText.substring(nameIndex + 2);
					} else {
						if (description.endsWith("-")) {
							description = description.substring(0, description.length() - 1);
							description += skillText;
						} else {
							description += " " + skillText;
						}
						newFeet = false;
					}
				} else {
					if (description.endsWith("-")) {
						description = description.substring(0, description.length() - 1);
						description += skillText;
					} else {
						description += " " + skillText;
					}

				}
				if (skillText.endsWith(".")) {
					feat.setDescription(description);
					feats.add(feat);
					feat = new Feat();
					newFeet = true;
				}
			} while (!skillText.trim().equals("Действия"));
			monster.setFeats(feats);

			// Действия
			List<Action> actions = new ArrayList<>();
			boolean newAction = true;
			Action action = new Action();
			action.setActionType(ActionType.ACTION);
			description = "";
			do {
				skillText = reader.readLine();
				if (skillText == null) {
					break;
				}
				if (newAction) {
					int nameIndex = skillText.indexOf(".");
					if (nameIndex != -1) {
						String nameAction = skillText.substring(0, nameIndex);
						action.setName(nameAction);
						newAction = false;
						description = skillText.substring(nameIndex + 2);
					} else {
						if (description.endsWith("-")) {
							description = description.substring(0, description.length() - 1);
							description += skillText;
						} else {
							description += " " + skillText;
						}
						newAction = false;
					}
				} else {
					if (description.endsWith("-")) {
						description = description.substring(0, description.length() - 1);
						description += skillText;
					} else {
						description += " " + skillText;
					}

				}
				if (skillText.endsWith(".")) {
					action.setDescription(description);
					actions.add(action);
					action = new Action();
					action.setActionType(ActionType.ACTION);
					newAction = true;
				}
			} while (skillText != null);
			monster.setActions(actions);

		} catch (IOException e) {
			e.printStackTrace();
		}
		repository.save(monster);
		return "parseMonster";
	}

	private void parseLanguage(String skillText, Creature monster) {
		List<Language> languageList = new ArrayList<>();
		skillText = skillText.replace("Языки ", "");
		String[] languages = skillText.split(",");
		for (String lang : languages) {
			Language language = languagesRepository.findByName(lang.trim());
			if (language == null) {
				language = new Language();
				language.setName(lang.trim());
				Language save = languagesRepository.save(language);
				language.setId(save.getId());
			}
			languageList.add(language);
		}
		monster.setLanguages(languageList);
	}

	private void parseSkill(String currentSkill, String skillText, Creature monster) {
		switch (currentSkill) {
		case "Спасброски": {
			List<SavingThrow> savingThrows = new ArrayList<>();
			skillText = skillText.replace("Спасброски ", "");
			String[] savingThrowsPair = skillText.split(",");
			for (String string : savingThrowsPair) {
				String[] parts = string.trim().split(" ");
				SavingThrow savingThrow = new SavingThrow();
				savingThrow.setAbility(Ability.parseShortName(parts[0].trim()));
				savingThrow.setBonus(Byte.parseByte(parts[1].trim()));
				savingThrows.add(savingThrow);
			}
			monster.setSavingThrows(savingThrows);
			break;
		}
		case "Навыки": {
			List<Skill> skillsList = new ArrayList<>();
			skillText = skillText.replace("Навыки ", "");
			String[] skills = skillText.split(",");
			for (String string : skills) {
				Skill skill = new Skill();
				String[] skillEl = string.trim().split(" ");
				skill.setType(SkillType.parse(skillEl[0].trim()));
				skill.setBonus(Byte.parseByte(skillEl[1].trim()));
				skillsList.add(skill);
			}
			monster.setSkills(skillsList);
			break;
		}
		case "Иммунитет к урону": {
			skillText = skillText.replace("Иммунитет к урону ", "");
			List<DamageType> damageList = getDamageTypes(skillText);
			monster.setImmunityDamages(damageList);
			break;
		}
		case "Сопротивление к урону": {
			skillText = skillText.replace("Сопротивление к урону ", "");
			List<DamageType> damageList = getDamageTypes(skillText);
			monster.setResistanceDamages(damageList);
			break;
		}
		case "Уязвимость к урону": {
			skillText = skillText.replace("Уязвимость к урону ", "");
			List<DamageType> damageList = getDamageTypes(skillText);
			monster.setVulnerabilityDamages(damageList);
			break;
		}
		case "Иммунитет к состоянию": {
			skillText = skillText.replace("Иммунитет к состоянию ", "");
			String[] stateTypes = skillText.split(",");
			List<State> immunityStateList = new ArrayList<>();
			for (String state : stateTypes) {
				immunityStateList.add(State.parse(state.trim()));
			}
			monster.setImmunityStates(immunityStateList);
			break;
		}
		}
	}

	private List<DamageType> getDamageTypes(String skillText) {
		List<DamageType> immunityDamageList = new ArrayList<>();
		String[] partDamages = null;
		if (skillText.contains(";")) {
			partDamages = skillText.split(";");
			skillText = partDamages[0];
		}

		String[] damageTypes = skillText.split(",");

		for (String damageType : damageTypes) {
			immunityDamageList.add(DamageType.parse(damageType.trim()));
		}
		if (partDamages != null) {
			immunityDamageList.add(DamageType.parse(partDamages[1].trim()));
		}
		return immunityDamageList;
	}
}