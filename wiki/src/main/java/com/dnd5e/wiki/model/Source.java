package com.dnd5e.wiki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum Source {
	PHB("Книга игрока"),
	DMG("Руководство мастера"),
	BESTIARY("Бестиарий"),
	XANATHARS("Руководство Зантара обо всем"),
	VOLO("Справочник Воло по монстрам"),
	PG("Путиводитель игрока"),
	EV("Элементальное зло"),
	MORDENKAIN("Том Морденкайнена о врагах"),
	TROT("Пробуждение Тиамат"),
	SCAG("Путеводитель приключенца по побережью меча");

	@Getter private String cyrilicName;
}
