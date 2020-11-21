package com.dnd5e.wiki.model.treasure;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TreasureType {
	COINS("Монеты"),
	GEM("Драгоценные камни"),
	WORKS_OF_ART("Произведения искусства"),
	BAUBLE("Безделушка"),
	GOTIC_BAUBLE("Готическая безделушка"),
	ELVEN_TRINKET("Эльфийская безделушка");
	
	private String name;

	public static TreasureType parse(String type) {
		return Arrays.stream(values())
				.filter(t -> t.getName().equals(type))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}