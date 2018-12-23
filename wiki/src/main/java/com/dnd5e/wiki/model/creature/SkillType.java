package com.dnd5e.wiki.model.creature;

import static com.dnd5e.wiki.model.creature.Ability.CHARISMA;
import static com.dnd5e.wiki.model.creature.Ability.DEXTERITY;
import static com.dnd5e.wiki.model.creature.Ability.INTELLIGENCE;
import static com.dnd5e.wiki.model.creature.Ability.STRENGTH;
import static com.dnd5e.wiki.model.creature.Ability.WISDOM;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Навыки 
 * @author VUnguryan
 *
 */
public enum SkillType {
	ATHLETICS(STRENGTH, "Атлетика"),
	ACROBATICS(DEXTERITY, "Акробатика"),
	SLEIGHT_OF_HAND(DEXTERITY, "Лoвкость рук", "Ловкость Рук"),
	STEALTH(DEXTERITY, "Скрытность"),
	ARCANA(INTELLIGENCE, "Магия"),
	HISTORY(INTELLIGENCE, "История"),
	INVESTIGATION(INTELLIGENCE, "Анализ"),
	NATURE(INTELLIGENCE, "Природа"),
	RELIGION(INTELLIGENCE, "Религия"),
	ANIMAL_HANDLING(WISDOM, "Уход за животными"),
	INSIGHT(WISDOM,"Проницательность"),
	MEDICINE(WISDOM, "Медицина"),
	PERCEPTION(WISDOM, "Внимательность", "Восприятие"),
	SURVIVAL(WISDOM, "Выживание"),
	DECEPTION(CHARISMA, "Обман"),
	INTIMIDATION(CHARISMA,  "Запугивание"),
	PERFORMANCE(CHARISMA, "Выступление"),
	PERSUASION(CHARISMA, "Убеждение");
	
	private String cyrilicName;
	private Set<String> cyrilicNames;
	private Ability ability;
	
	SkillType(Ability ability, String ... cyrilicNames){
		this.ability = ability;
		this.cyrilicName = cyrilicNames[0];
		this.cyrilicNames = Arrays.stream(cyrilicNames).collect(Collectors.toSet());
	}

	public String getCyrilicName() {
		return cyrilicName;
	}

	public static SkillType parse(String cyrilicName) {
		for (SkillType type : values()) {
			if (type.cyrilicNames.contains(cyrilicName)) {
				return type;
			}
		}
		return null;
	}
	public Ability getAbility() {
		return ability;
	}
}