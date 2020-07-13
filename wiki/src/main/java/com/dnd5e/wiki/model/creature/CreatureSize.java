package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreatureSize {
	TINY("Крошечный"),
	SMALL("Маленький"),
	MEDIUM("Средний"), 
	LARGE("Большой"),
	HUGE("Огромный"),
	GARGANTUAN("Громадный"); 

	private String cyrilicName;

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