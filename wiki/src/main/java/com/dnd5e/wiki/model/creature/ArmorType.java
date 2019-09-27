package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArmorType {
	NATURAL("природный доспех"),
	LEATHER("кожанный доспех"),
	HIDE("шкурный доспех"),
	CHAINMAIL("кольчужная рубаха"),
	SCRAPPY("лоскутный доспех"),
	SCALED("чешуйчатый доспех"),
	PLATE("латный доспех"),
	RIVETED_LEATHER("проклёпанный кожаный доспех");

	private String cyrillicName;
}