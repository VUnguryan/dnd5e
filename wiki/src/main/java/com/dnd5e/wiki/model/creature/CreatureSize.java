package com.dnd5e.wiki.model.creature;

import lombok.Getter;

@Getter
public enum CreatureSize {
	TINY("Крошечный","Крошечная", "Крошечное"),
	SMALL("Маленький", "Маленькая", "Маленькое"),
	MEDIUM("Средний", "Средняя", "Среднее"), 
	LARGE("Большой", "Большая", "Большое"),
	HUGE("Огромный", "Огромная", "Огромное"),
	GARGANTUAN("Громадный", "Громадная", "Громадное"); 

	private String [] names;
	CreatureSize(String... names){
		this.names = names;
	}

	public static CreatureSize parse(String size) {
		for (CreatureSize creatureSize : values()) {
			for (String sizeName : creatureSize.names) {
				if (sizeName.equalsIgnoreCase(size)) {
					return creatureSize;
				}
			}
		}
		return null;
	}

	public String getSizeName(CreatureType type) {
		switch (type) {
		case ABERRATION:
		case FEY:
		case OOZE:
		case UNDEAD:
		case SLIME:
		case SMALL_BEAST:
			return names[1];
		case FIEND:
		case MONSTROSITY:
		case PLANT:
			return names[2];
		default:
			return names[0];
		}
	}

	public String getCyrilicName() {
		return names[0];
	}
}