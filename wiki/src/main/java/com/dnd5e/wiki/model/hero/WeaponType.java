package com.dnd5e.wiki.model.hero;

public enum WeaponType {
	SIMPLE_MELE("Простое рукопашное"),
	SIMPLE_RANGED("Простое дальнобойное"),
	WAR_MELE("Воинское рукопашное"),
	WAR_RANGED("Воинское дальнобойное"),
	EXOTIC_MELE("Экзотическое рукопашное оружие"), 
	EXOTIC_RANGED("Экзотическое дальнобойное оружие");
	
	private String name;

	WeaponType(String name){
		this.name= name;
	}
	
	public String getName() {
		return name;
	}
}
