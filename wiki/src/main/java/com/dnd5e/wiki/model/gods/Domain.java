package com.dnd5e.wiki.model.gods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Domain {
	KNOWLEDGE("Знание"),
	WAR("Война"),
	STORM("Буря"),
	DEATH("Смерть"),
	DECEPTION("Обман"),
	LIFE("Жизнь"),
	NATURE("Природа"),
	LIGHT("Свет"),
	ARCANA("Магия"),
	UNDEFINE("Нет жрецов");

	private String cyrilicName;
}
