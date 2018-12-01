package com.dnd5e.wiki.model.artifact;

public enum Rarity {
	COMMON("обычный", 100),
	UNCOMON("необычный", 400),
	RARE("редкий", 4000),
	VERY_RARE("очень редкий", 40000),
	LEGENDARY("легендарный", 200000);

	private String cyrilicName;
	// базовая цена в золотых монетах
	private int baseCost;

	private Rarity(String cyrilicName, int baseCost) {
		this.cyrilicName = cyrilicName;
		this.baseCost = baseCost;
	}

	public String getCyrilicName() {
		return this.cyrilicName;
	}

	public int getBaseCost() {
		return baseCost;
	}
}