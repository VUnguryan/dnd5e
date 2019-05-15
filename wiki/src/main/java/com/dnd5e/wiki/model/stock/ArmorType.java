package com.dnd5e.wiki.model.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArmorType {
	LIGHT("Лёгкий доспех", "1 минута", "1 минута", "+ модификатор Лов"),
	MEDIUM("Средний доспех", "5 минут",	"1 минута", "+ модификатор Лов (макс. 2)"),
	HEAVY("Тяжёлый доспех", "10 минут", "5 минут", ""),
	SHIELD("Щит", "1 действие", "1 действие", "");

	private String name;
	private String dressingTime;
	private String removalTime;
	private String bonus ="";

}