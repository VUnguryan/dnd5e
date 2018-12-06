package com.dnd5e.wiki.model.spell;

public enum TimeCast {
	BONUS("1 бонусное действие"), 
	ACTION("1 действие"), 
	REACTION("1 реакция"),
	MINUTE1("1 минута"),
	MINUTE10("10 минут"),
	HOUR("1 час"),
	HOUR8("8 часов"),
	HOUR12("12 часов"),
	HOUR24("24 часа");

	private String name;

	TimeCast(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
