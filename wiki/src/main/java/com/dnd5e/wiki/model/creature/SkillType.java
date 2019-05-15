package com.dnd5e.wiki.model.creature;

import static com.dnd5e.wiki.model.creature.Ability.CHARISMA;
import static com.dnd5e.wiki.model.creature.Ability.DEXTERITY;
import static com.dnd5e.wiki.model.creature.Ability.INTELLIGENCE;
import static com.dnd5e.wiki.model.creature.Ability.STRENGTH;
import static com.dnd5e.wiki.model.creature.Ability.WISDOM;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;

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
	
	@Getter() private Ability ability;
	@Getter() private String cyrilicName;
	private Set<String> cyrilicNames;

	
	SkillType(Ability ability, String ... cyrilicNames){
		this.ability = ability;
		this.cyrilicName = cyrilicNames[0];
		this.cyrilicNames = Arrays.stream(cyrilicNames).collect(Collectors.toSet());
	}

	public static SkillType parse(String cyrilicName) {
		for (SkillType type : values()) {
			if (type.cyrilicNames.contains(cyrilicName)) {
				return type;
			}
		}
		return null;
	}
	
	public static Map<Ability, List<SkillType>> getSkillsToAbbility() {
		return Arrays.stream(values()).collect(sortedGroupingBy(SkillType::getAbility));
	}
	
	public static <T, K extends Comparable<K>> Collector<T, ?, TreeMap<K, List<T>>> sortedGroupingBy(
			Function<T, K> function) {
		return Collectors.groupingBy(function, TreeMap::new, Collectors.toList());
	}
}