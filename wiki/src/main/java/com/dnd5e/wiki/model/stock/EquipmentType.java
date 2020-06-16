package com.dnd5e.wiki.model.stock;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EquipmentType {
	EAT_DRINK("Еда и напитки"),
	TOOL("Инструменты"),
	SET("Наборы"),
	CONTAINER("Контейнеры"),
	FOCUSING("Магическая фокусировка"),
	DRUID_FOCUS("Фокусировка друидов"),
	HOLY_SYMBOL("Священный символ"),
	AMMUNITION("Боеприпасы"),
	POISON("Яды и противоядия"), 
	CLOTHES("Одежда"), 
	GAME_SET("Игровой набор"),
	ARTISANS_TOOLS("Инструменты ремесленников"),
	MUSICAL_INSTRUMENTS("Музыкальные инструменты");
	
	private String cyrilicName;

	public static EquipmentType parse(String type) {
		return Arrays.stream(values())
				.filter(t -> t.cyrilicName.equals(type))
				.findFirst()
					.orElseThrow(IllegalArgumentException::new);
	}
}