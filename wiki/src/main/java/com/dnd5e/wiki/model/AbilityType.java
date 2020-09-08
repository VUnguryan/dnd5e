package com.dnd5e.wiki.model;

import org.thymeleaf.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Характеристики 
 */
@Getter
@AllArgsConstructor
public enum AbilityType {
	STRENGTH("Сила"),             // 0 
	DEXTERITY("Ловкость"),        // 1
	CONSTITUTION("Телосложение"), // 2
	INTELLIGENCE("Интеллект"),    // 3
	WISDOM("Мудрость"),           // 4
	CHARISMA("Харизма");          // 5

	private String cyrilicName;

	public String getShortName() {
		return cyrilicName.substring(0,3);
	}

	public static AbilityType parseShortName(String shortName) {

		switch (shortName) {
			case "Сил":
				return AbilityType.STRENGTH;
			case "Лов":
				return AbilityType.DEXTERITY;
			case "Тел":
				return AbilityType.CONSTITUTION;
			case "Инт":
				return AbilityType.INTELLIGENCE;
			case "Мдр":
			case "Муд":			
				return AbilityType.WISDOM;
			case "Хар":
				return AbilityType.CHARISMA;
		}
		return null;
	}
	
	public String getCapitalizeName() {
		return StringUtils.capitalize(name().toLowerCase());
	}
	
	public static AbilityType parse(String name) {
		for (AbilityType abilityType : values()) {
			if (abilityType.getCyrilicName().equalsIgnoreCase(name)) {
				return abilityType;
			}
		}
		return null;
	}
}