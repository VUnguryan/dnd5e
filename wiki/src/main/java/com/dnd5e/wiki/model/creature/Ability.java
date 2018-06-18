package com.dnd5e.wiki.model.creature;

public enum Ability {
	STRENGTH("Сила"),
	DEXTERITY("Ловкость"),
	CONSTITUTION("Телосложение"),
	INTELLIGENCE("Интеллект"),
	WISDOM("Мудрость"),
	CHARISMA("Харизма");

	private String cytilicName;

	Ability(String cytilicName) {
		this.cytilicName = cytilicName;
	}

	public String getCytilicName() {
		return cytilicName;
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
