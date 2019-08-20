package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AbilityType {
	STRENGTH("Сила"),
	DEXTERITY("Ловкость"),
	CONSTITUTION("Телосложение"),
	INTELLIGENCE("Интеллект"),
	WISDOM("Мудрость"),
	CHARISMA("Харизма");

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
	
	public static AbilityType parse(String name) {
		for (AbilityType abilityType : values()) {
			if (abilityType.getCyrilicName().equalsIgnoreCase(name)) {
				return abilityType;
			}
		}
		return null;
	}
}
