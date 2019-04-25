package com.dnd5e.wiki.controller;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.SavingThrow;
import com.dnd5e.wiki.model.creature.Skill;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.creature.State;
import com.dnd5e.wiki.model.feat.Trait;
import com.dnd5e.wiki.repository.CreatureRaceRepository;
import com.dnd5e.wiki.repository.CreatureRepository;
import com.dnd5e.wiki.repository.LanguagesRepository;

@Controller
@RequestMapping({ "/creatures" })
@Scope("session")
public class CreatureController {
	private static final List<String> statusNames = Arrays.asList("Спасброски", "Навыки", "Иммунитет к урону",
			"Сопротивление к урону", "Сопротивление урону", "Уязвимость к урону", "Иммунитет к состоянию",
			"Иммунитет к состояниям", "Чувства");

	private CreatureRepository repository;
	private LanguagesRepository languagesRepository;
	private CreatureRaceRepository creatureRaceRepository;

	private Optional<String> search = Optional.empty();
	private Optional<String> crMin = Optional.empty();
	private Optional<String> crMax = Optional.empty();
	private Optional<CreatureType> typeSelected = Optional.empty();
	private Optional<CreatureSize> sizeSelected = Optional.empty();

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

	@GetMapping
	public String getCreatures(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<Creature> specification = null;
		if (search.isPresent()) {
			specification = byName();
		}
		if (typeSelected.isPresent()) {
			specification = (specification == null) ? byType() : Specification.where(specification).and(byType());
		}
		if (crMin.isPresent()) {
			specification = (specification == null) ? byMinExp() : Specification.where(specification).and(byMinExp());
		}
		if (crMax.isPresent()) {
			specification = (specification == null) ? byMaxExp() : Specification.where(specification).and(byMaxExp());
		}
		if (sizeSelected.isPresent()) {
			specification = (specification == null) ? bySize() : Specification.where(specification).and(bySize());
		}
		model.addAttribute("creatures", repository.findAll(specification, page));
		model.addAttribute("filter",
				crMin.isPresent() 
				|| crMax.isPresent() 
				|| search.isPresent() 
				|| typeSelected.isPresent() 
				|| sizeSelected.isPresent());
		model.addAttribute("searchText", search);
		model.addAttribute("crMin", crMin);
		model.addAttribute("crMax", crMax);
		model.addAttribute("types", CreatureType.values());
		model.addAttribute("sizes", CreatureSize.values());
		model.addAttribute("typeSelected", typeSelected);
		model.addAttribute("sizeSelected", sizeSelected);
		return "creatures";
	}

	@GetMapping("/creature/{id}")
	public String getCreaturView(Model model, @PathVariable Integer id) {
		Creature creature = repository.findById(id).get();
		model.addAttribute("creature", creature);
		List<Action> actions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION)
				.collect(Collectors.toList());
		model.addAttribute("actions", actions);
		List<Action> reactions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION)
				.collect(Collectors.toList());
		model.addAttribute("reactions", reactions);
		List<Action> legendary = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY)
				.collect(Collectors.toList());
		model.addAttribute("legendary", legendary);
		return "creatureView";
	}

	@GetMapping("/creature/classic/{id}")
	public String getClassicCreature(Model model, @PathVariable Integer id) {
		Creature creature = repository.findById(id).get();
		model.addAttribute("creature", creature);
		List<Action> actions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION)
				.collect(Collectors.toList());
		model.addAttribute("actions", actions);
		List<Action> reactions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION)
				.collect(Collectors.toList());
		model.addAttribute("reactions", reactions);
		List<Action> legendary = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY)
				.collect(Collectors.toList());
		model.addAttribute("legendary", legendary);
		return "classicCreatureView";
	}

	@GetMapping("/race/{id}")
	public String getCreatureRace(Model model, @PathVariable Integer id) {
		CreatureRace race = creatureRaceRepository.getOne(id);
		model.addAttribute("race", race);
		model.addAttribute("creatures", repository.findAllByRaceIdOrderByExpAsc(id));
		return "classesCreature";
	}

	@GetMapping(params = "search")
	public String searchCreature(Model model, String search) {
		this.search = search.isEmpty() ? this.search = Optional.empty() : Optional.of(search);
		return "redirect:/creatures";
	}

	@GetMapping(params = { "sort", "type", "crMin", "crMax", "cSize" })
	public String filter(Model model, String sort, String type, String crMin, String crMax, String cSize) {
		this.typeSelected = "ALL".equals(type) ? Optional.empty() : Optional.of(CreatureType.valueOf(type));
		this.crMin = "-1".equals(crMin) ? Optional.empty() : Optional.of(crMin);
		this.crMax = "-1".equals(crMax) ? Optional.empty() : Optional.of(crMax);
		this.sizeSelected = "ALL".equals(cSize) ? Optional.empty() : Optional.of(CreatureSize.valueOf(cSize));
		return "redirect:/creatures?sort=" + sort;
	}

	@GetMapping("/add")
	public String getMonsterForm(Model model) {
		model.addAttribute("monster", new Creature());
		return "parseMonster";
	}

	@PostMapping("/add")
	public String createCreature(Model model, String description) {
		Creature creature = new Creature();
		try (LineNumberReader reader = new LineNumberReader(new StringReader(description))) {
			String name = reader.readLine();
			int index = name.indexOf('[');
			if (index > 0) {
				String otherName = name.substring(index + 1, name.indexOf(']'));
				creature.setName(name.substring(0, index).trim());
				creature.setEnglishName(otherName.trim());
			} else {
				creature.setName(name.trim());
			}
			String sizeTypeAligmentString = reader.readLine();
			String[] sizeTypeAligment = sizeTypeAligmentString.split(",");
			String[] sizeType = sizeTypeAligment[0].split("\\s");

			// размер
			String size = sizeType[0];
			creature.setSize(CreatureSize.parse(size.trim()));
			// тип
			String type = sizeType[1];
			creature.setType(CreatureType.parse(type.trim()));
			if (sizeType.length > 2) {
				CreatureRace race = creatureRaceRepository.findByName(sizeType[2].trim());
				if (race == null) {
					race = new CreatureRace();
					race.setName(sizeType[2].trim());
					race = creatureRaceRepository.save(race);
				}
				creature.setRaceName(race.getName());
				creature.setRaceId(race.getId());
			}
			// мировозрение
			if (sizeTypeAligment.length > 1) {
				creature.setAlignment(Alignment.parse(sizeTypeAligment[1].trim()));
			}
			// доспех
			String armorString = reader.readLine();
			if (armorString.contains("Класс Доспеха")) {
				String[] armor = armorString.split(" ");
				byte ac = Byte.parseByte(armor[2].trim());
				creature.setAC(ac);
			}

			// Хиты
			String hitsString = reader.readLine();
			if (hitsString.contains("Хиты")) {
				String[] hits = hitsString.split("\\s");
				creature.setAverageHp(Short.parseShort(hits[1].trim()));
				hits[2] = hits[2].replace("(", "");
				String[] dices = hits[2].split("к");
				creature.setCountDiceHp(Short.parseShort(dices[0].trim()));
				dices[1] = dices[1].replace(")", "");
				int dice = Short.parseShort(dices[1].trim());
				creature.setDiceHp(Dice.parse(dice));
				if (hits.length > 3) {
					hits[3] = hits[3].replaceAll("[^0-9]", "").trim();
					if (!hits[3].isEmpty()) {
						creature.setBonusHP(Short.parseShort(hits[3]));
					} else if (hits.length > 4) {
						hits[4] = hits[4].replaceAll("[^0-9]", "").trim();
						creature.setBonusHP(Short.parseShort(hits[4]));
					}
				}
			}

			// Скорость
			String speedString = reader.readLine();
			if (speedString.contains("Скорость")) {
				String[] speeds = speedString.split(",");
				speeds[0] = speeds[0].replaceAll("[^0-9]", "");
				creature.setSpeed(Short.parseShort(speeds[0].trim()));
				for (int i = 1; i < speeds.length; i++) {
					String otherSpeed = speeds[i];
					if (otherSpeed.contains("летая") || otherSpeed.contains("полёт")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]", "").trim();
						creature.setFlySpeed(Short.parseShort(otherSpeed));
					} else if (otherSpeed.contains("плавая")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]", "").trim();
						creature.setSwimmingSpped(Short.parseShort(otherSpeed));
					} else if (otherSpeed.contains("лазая") || otherSpeed.contains("взбирание")) {
						otherSpeed = otherSpeed.replaceAll("[^0-9]", "").trim();
						creature.setClimbingSpeed(Short.parseShort(otherSpeed));
					}
				}
			}
			reader.readLine();
			String abilitesString = reader.readLine();
			abilitesString = abilitesString.replace("–", "+");
			abilitesString = abilitesString.replaceAll("\\(\\s?\\+[0-9]{1,2}\\s?\\)", "");
			String[] abilites = abilitesString.split("\\s+");
			creature.setStrength(Byte.valueOf(abilites[0]));
			creature.setDexterity(Byte.valueOf(abilites[1]));
			creature.setConstitution(Byte.valueOf(abilites[2]));
			creature.setIntellect(Byte.valueOf(abilites[3]));
			creature.setWizdom(Byte.valueOf(abilites[4]));
			creature.setCharisma(Byte.valueOf(abilites[5]));

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
								parseSkill(currentSkill, skillText, creature);
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
				creature.setVision(skillText);
				skillText = reader.readLine();
			} else {
				creature.setVision(skillText);
				skillText = token;
			}

			// языки
			token = reader.readLine();
			if (!token.startsWith("Опасность")) {
				skillText += " " + token;
				if (skillText.startsWith("Языки")) {
					parseLanguage(skillText, creature);
				}
			} else {
				parseLanguage(skillText, creature);
				skillText = token;
			}

			// Опасность
			if (!skillText.startsWith("Опасность")) {
				skillText = reader.readLine();
			}
			skillText = skillText.replace("Опасность ", "").trim();
			String[] crAndExp = skillText.split(" ");
			creature.setChallengeRating(crAndExp[0].trim());
			creature.setExp(Integer.valueOf(crAndExp[1].replaceAll("[^0-9]", "").trim()));

			// Фиты
			Trait feat = new Trait();
			List<Trait> feats = new ArrayList<>();
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
					feat = new Trait();
					newFeet = true;
				}
			} while (!skillText.trim().equals("Действия"));
			creature.setFeats(feats);

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
			creature.setActions(actions);

		} catch (IOException e) {
			e.printStackTrace();
		}
		repository.save(creature);
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
				String[] skillEl = string.trim().split("\\+");
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
		case "Сопротивление к урону":
		case "Сопротивление урону": {
			skillText = skillText.replace("Сопротивление к урону ", "");
			skillText = skillText.replace("Сопротивление урону ", "");
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
		case "Иммунитет к состоянию":
			skillText = skillText.replace("Иммунитет к состоянию ", "");
		case "Иммунитет к состояниям": {
			skillText = skillText.replace("Иммунитет к состояниям ", "");
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

	private Specification<Creature> byName() {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search.get() + "%"),
				cb.like(root.get("englishName"), "%" + search.get() + "%"));
	}

	private Specification<Creature> byType() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("type"), typeSelected.get()));
	}

	private Specification<Creature> byMinExp() {
		return (root, query, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get("exp"), toExp(crMin.get())));
	}

	private Specification<Creature> byMaxExp() {
		return (root, query, cb) -> cb.and(cb.lessThanOrEqualTo(root.get("exp"), toExp(crMax.get())));
	}

	private Specification<Creature> bySize() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("size"), sizeSelected.get()));
	}

	private int toExp(String cr) {
		switch (cr) {
		case "0":
			return 10;
		case "1/8":
			return 25;
		case "1/4":
			return 50;
		case "1/2":
			return 100;
		case "1":
			return 200;
		case "2":
			return 450;
		case "3":
			return 700;
		case "4":
			return 1100;
		case "5":
			return 1800;
		case "6":
			return 2300;
		case "7":
			return 2900;
		case "8":
			return 3900;
		case "9":
			return 5000;
		case "10":
			return 5900;
		case "11":
			return 7200;
		case "12":
			return 8400;
		case "13":
			return 10000;
		case "14":
			return 11500;
		case "15":
			return 13000;
		case "16":
			return 15000;
		case "17":
			return 18000;
		case "18":
			return 20000;
		case "19":
			return 22000;
		case "20":
			return 25000;
		case "21":
			return 25000;
		case "22":
			return 41000;
		case "23":
			return 50000;
		case "24":
			return 62000;
		case "25":
			return 75000;
		case "26":
			return 90000;
		case "27":
			return 105000;
		case "28":
			return 120000;
		case "29":
			return 135000;
		case "30":
			return 155000;
		}
		return 0;
	}
}