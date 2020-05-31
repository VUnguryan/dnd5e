package com.dnd5e.wiki.model.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EquipmentType {
	EAT_DRINK("Еда и напитки"),
	TOOL("Инструменты"),
	SET("Наборы"),
	CONTAINER("Контейнеры"),
	FOCUSING("Фокусировки"),
	HOLY_SYMBOL("Священный символ"),
	AMMUNITION("Боеприпасы"),
	POISON("Яды и противоядия");
	private String cyrilicName;
}
