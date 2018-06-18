package com.dnd5e.wiki.model.creature;

public enum ArmorType {
	NATURAL("природный доспех"),
	LEATHER("кожанный доспех"),
	CHAINMAIL("кольчужная рубаха"),
	PLATE("латы"),;

	private String cyrillicName;

	private ArmorType(String cyrillicName) {
		this.cyrillicName = cyrillicName;
	}

	public String getCyrillicName() {
		return this.cyrillicName;
	}
}