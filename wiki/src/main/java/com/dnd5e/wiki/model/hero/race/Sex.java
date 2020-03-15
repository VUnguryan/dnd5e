package com.dnd5e.wiki.model.hero.race;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sex {
	MALE("Мужские"),
	FEMALE("Женские"),
	CHILD("Детские"),
	UNISEX("Унисекс");
	
	private String cyrilicName;
}
