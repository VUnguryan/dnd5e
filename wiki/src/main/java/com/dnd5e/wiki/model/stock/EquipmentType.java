package com.dnd5e.wiki.model.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EquipmentType {
	TOOL("Инструмент"),
	SET("Набор");
	private String cyrilicName;
}
