package com.dnd5e.wiki.model.hero.race;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sex {
	MALE("Мужчина"),
	FEMALE("Женщина"),
	CHILD("Детские"),
	UNISEX("Унисекс");
	private String cyrilicName;
}
