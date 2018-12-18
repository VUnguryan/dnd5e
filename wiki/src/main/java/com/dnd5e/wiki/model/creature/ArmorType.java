package com.dnd5e.wiki.model.creature;

public enum ArmorType {
	NATURAL("природный доспех"),
	LEATHER("кожанный доспех"),
	HIDE("шкурный доспех"),
	CHAINMAIL("кольчужная рубаха"),
	SCRAPPY("лоскутный доспех"),
	SCALED("чешуйчатый доспех"),
	PLATE("латный доспех");

	private String cyrillicName;

	private ArmorType(String cyrillicName) {
		this.cyrillicName = cyrillicName;
	}

	public String getCyrillicName() {
		return this.cyrillicName;
	}
}