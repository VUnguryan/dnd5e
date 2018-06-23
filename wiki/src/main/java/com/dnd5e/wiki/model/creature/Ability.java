package com.dnd5e.wiki.model.creature;

public enum Ability {
	STRENGTH("Сила"),
	DEXTERITY("Ловкость"),
	CONSTITUTION("Телосложение"),
	INTELLIGENCE("Интеллект"),
	WISDOM("Мудрость"),
	CHARISMA("Харизма");

	private String cyrilicName;

	Ability(String cyrilicName) {
		this.cyrilicName = cyrilicName;
	}

	public String getCyrilicName() {
		return cyrilicName;
	}
	
	public String getShortName() {
		return cyrilicName.substring(0,3);
	}

	public static Ability parseShortName(String shortName) {

		switch (shortName) {
		case "Сил":
			return Ability.STRENGTH;
		case "Лов":
			return Ability.DEXTERITY;
		case "Тел":
			return Ability.CONSTITUTION;
		case "Инт":
			return Ability.INTELLIGENCE;
		case "Мдр":
			return Ability.WISDOM;
		case "Хар":
			return Ability.CHARISMA;
		}
		return null;
	}
}
