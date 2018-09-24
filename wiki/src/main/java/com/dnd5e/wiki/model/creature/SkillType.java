package com.dnd5e.wiki.model.creature;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Навыки 
 * @author VUnguryan
 *
 */
public enum SkillType {
	ATHLETICS("Атлетика"),
	ACROBATICS("Акробатика"),
	SLEIGHT_OF_HAND("Лoвкость рук", "Ловкость рук"),
	STEALTH("Скрытность"),
	ARCANA("Магия"),
	HISTORY("История"),
	INVESTIGATION("Анализ"),
	NATURE("Природа"),
	RELIGION("Религия"),
	ANIMAL_HANDLING("Уход за животными"),
	INSIGHT("Проницательность"),
	MEDICINE("Медицина"),
	PERCEPTION("Внимательность", "Восприятие"),
	SURVIVAL("Выживание"),
	DECEPTION("Обман"),
	INTIMIDATION("Запугивание"),
	PERFORMANCE("Выступление"),
	PERSUASION("Убеждение");
	
	private String cyrilicName;
	private Set<String> cyrilicNames;
	
	SkillType(String ... cyrilicNames){
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
}