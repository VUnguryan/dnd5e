package com.dnd5e.wiki.model.stock;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EquipmentType {
	ADVENTURING_GEAR("Cнаряжение"),
	
	EAT_DRINK("Еда и напитки"),
	TOOL("Инструменты"),
	SET("Наборы"),
	CONTAINER("Контейнеры"),
	FOCUSING("Магическая фокусировка"),
	DRUID_FOCUS("Фокусировка друидов"),
	HOLY_SYMBOL("Священный символ"),
	POISON("Яды и противоядия"), 
	CLOTHES("Одежда"), 
	GAME_SET("Игровой набор"),
	ARTISANS_TOOLS("Инструменты ремесленников"),
	MUSICAL_INSTRUMENTS("Муз. инструменты"),
	THROWABLE("Бросаемое"),
	LIGHT("Освещение"),
	
	ARMOR("Доспех"),
	LIGHT_ARMOR("Лёгкий доспех"),
	MEDIUM_ARMOR("Средний доспех"),
	HEAVY_ARMOR("Тяжелый доспех"),
	
	WEAPON("Оружие"),
	SIMPLE_WEAPON("Простое оружие"),
	MARTIAL_WEAPON("Воинское оружие"),
	EXOTIC_WEAPON("Экзотическое оружие"),
	MELE_WAPON("Рукопашное оружие"), 
	RANGE_WAPON("Дальнобойное оружие"),
	FIRE_WEAPON("Огнестрельное оружие"),
	AMMUNITION("Боеприпасы"),
	SHIELD("Щит"),

	TRADE_GOOD("Товары"),
	
	MOUNT("Ездовое животное"),
	VEHICLE_WATER("Транспорт (водный)"),
	VEHICLE_LAND("Транспорт (наземный)"),
	VEHICLE_AIR("Транспорт (воздушный)");
	
	private String cyrilicName;

	public static EquipmentType parse(String type) {
		return Arrays.stream(values())
				.filter(t -> t.cyrilicName.equals(type))
				.findFirst()
					.orElseThrow(IllegalArgumentException::new);
	}
}