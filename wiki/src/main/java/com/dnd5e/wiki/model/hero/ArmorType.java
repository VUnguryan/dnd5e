package com.dnd5e.wiki.model.hero;

public enum ArmorType {
	LIGHT("Лёгкий доспех"),
	MEDIUM("Средний доспех"),
	HEAVY("Тяжёлый доспех"),
	SHIELD("Щит");
	
	private String name;
	
	ArmorType(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
