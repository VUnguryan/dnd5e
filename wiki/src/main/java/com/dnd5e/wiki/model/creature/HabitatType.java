package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HabitatType {
	ARCTIC("полярная тундра", "arctic"),
	COAST("побережье", "coastal"),
	WATERS("под водой", "underwater"),
	GRASSLAND("равнина/луг", "grassland"),
	UNDERGROUND("подземье", "underdark"),
	CITY("город", "urban"),
	VILLAGE("деревня", null),
	RUINS("руины", null),
	DUNGEON("подземелья", null),
	FOREST("лес", "forest"),
	HILL("холмы", "hill"),
	MOUNTAIN("горы", "mountain"),
	SWAMP("болото", "swamp"),
	DESERT("пустыня", "desert"),
	TROPICS("тропики", null);
	private String name;
	private String xmlName;
	
	public boolean isXml() {
		return xmlName != null;
	}
}
