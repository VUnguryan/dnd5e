package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HabitatType {
	ARCTIC("полярная тундра"),
	COAST("побережье"),
	WATERS("на воде"),
	GRASSLAND("равнина"),
	UNDERGROUND("подземье"),
	CITY("город"),
	VILLAGE("деревня"),
	RUINS("руины"),
	DUNGEON("подземелье"),
	FOREST("лес"),
	MOUNTAIN("горы"),
	SWAMP("болото"),
	DESERT("пустыня"),
	TROPICS("тропики");
	private String name;
}
