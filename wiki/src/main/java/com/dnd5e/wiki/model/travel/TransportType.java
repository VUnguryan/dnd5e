package com.dnd5e.wiki.model.travel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransportType {
	HORSE("СКАКУНЫ И ДРУГИЕ ЖИВОТНЫЕ"),
	GROUND_TRANSPORTATION("НАЗЕМНЫЙ ТРАНСПОРТ"),
	WATER_TRANSPORTATION("ВОДНЫЙ ТРАНСПОРТ"),
	AIR_TRANSPORTATION("ВОЗДУШНЫЙ ТРАНСПОРТ");

	private String name;
}
