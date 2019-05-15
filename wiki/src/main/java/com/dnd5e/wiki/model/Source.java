package com.dnd5e.wiki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Source {
	PHB("Книга игрока"),
	DMG("Руководство мастера"),
	BESTIARY("Бестиарий"),
	XANATHARS("Руководство Зантара обо всем"),
	VOLO("Справочник Воло по монстрам"),
	PG("Путиводитель игрока"),
	EV("Элементальное зло");

	private String cyrilicName;
}
