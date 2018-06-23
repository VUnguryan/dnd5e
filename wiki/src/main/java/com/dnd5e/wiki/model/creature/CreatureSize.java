package com.dnd5e.wiki.model.creature;

public enum CreatureSize {
	TINY("Крошечный"),
	SMALL("Маленький"),
	MEDIUM("Средний"), 
	LARGE("Большой"),
	HUGE("Огромный"),
	GARGANTUM("Громадный");

	private String cyrilicName;

	private CreatureSize(String cyrilicName) {
		this.cyrilicName = cyrilicName;
	}

	public String getCyrilicName() {
		return this.cyrilicName;
	}

	public static CreatureSize parse(String size) {
		for (CreatureSize creatureSize : values()) {
			String start = creatureSize.cyrilicName.substring(0, 3);
			if (size.startsWith(start)) {
				return creatureSize;
			}
		}
		return null;
	}
}