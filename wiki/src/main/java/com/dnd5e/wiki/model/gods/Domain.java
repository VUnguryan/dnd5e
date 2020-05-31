package com.dnd5e.wiki.model.gods;

import java.util.Arrays;

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
	FORGE("Кузня"),
	UNDEFINE("Нет жрецов");

	private String cyrilicName;
	public static Domain parse(String value) {
		return Arrays.stream(values())
				.filter(d -> d.getCyrilicName().equals(value))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}