package com.dnd5e.wiki.model.creature;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum CreatureType {
	  ABERRATION("аберрация"),  
	  BEAST("зверь"),
	  CELESTIAL("небожитель"),
	  CONSTRUCT("конструкт"),
	  DRAGON("дракон"),
	  ELEMENTAL("элементаль"),
	  FEY("фея", "фей"),
	  FIEND("исчадие"),
	  GIANT("великан", "гигант"),
	  HUMANOID("гуманоид"),
	  MONSTROSITY("монстр"),
	  OOZE("тина"),
	  OUTSIDER("потустаронний"),
	  PLANT("растение"),
	  DEVIL("демон", "изверг"),
	  UNDEAD("нежить"),
	  VERMIN("паразит"), 
	  SLIME("слизь"),
	  SMALL_BEAST("Крохотных зверей");

	private String cyrilicName;
	private Set<String> cyrilicNames;

	private CreatureType(String... cyrilicNames) {
		this.cyrilicName = cyrilicNames[0];
		this.cyrilicNames = new HashSet<>(Arrays.asList(cyrilicNames));
	}
	public String getCyrilicName() {
		return this.cyrilicName;
	}

	public static CreatureType parse(String type) {
		for (CreatureType creatureType : values()) {
			if (creatureType.cyrilicNames.contains(type)) {
				return creatureType;
			}
		}
		return null;
	}
}
