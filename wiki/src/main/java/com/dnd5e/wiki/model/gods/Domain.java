package com.dnd5e.wiki.model.gods;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Domain {
	ARCANA("Магия"),
	DEATH("Смерть"),
	FORGE("Кузня"),
	GRAVE("Могила"),
	KNOWLEDGE("Знание"),
	LIFE("Жизнь"),
	LIGHT("Свет"),
	NATURE("Природа"),
	NONE("Нет"),
	ORDER("Порядок"),
	TEMPEST("Буря"),
	TRICKERY("Обман"),
	WAR("Война"),

	STORM("Буря"),
	DECEPTION("Обман"),
	UNDEFINE("Нет жрецов"); 

	private String cyrilicName;
	public static Domain parse(String value) {
		return Arrays.stream(values())
				.filter(d -> d.getCyrilicName().equals(value))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}