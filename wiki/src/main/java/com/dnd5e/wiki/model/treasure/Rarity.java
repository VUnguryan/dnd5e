package com.dnd5e.wiki.model.treasure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rarity {
	COMMON("обычный", 100),
	UNCOMMON("необычный", 400),
	RARE("редкий", 4000),
	VERY_RARE("очень редкий", 40_000),
	LEGENDARY("легендарный", 200_000),
	ARTIFACT("артефакт", 1_500_000);

	private String cyrilicName;
	// базовая цена в золотых монетах
	private int baseCost;
}