package com.dnd5e.wiki.model.creature;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum CreatureType {
	  ABERRATION("аберрация"), // 0  
	  BEAST("зверь"), // 1
	  CELESTIAL("небожитель"), //2 
	  CONSTRUCT("конструкт"), // 3
	  DRAGON("дракон"), //4
	  ELEMENTAL("элементаль"), //5
	  FEY("фея", "фей"), //6 
	  FIEND("исчадие"), //7
	  GIANT("великан", "гигант"), //8
	  HUMANOID("гуманоид"), //9
	  MONSTROSITY("чудовище", "монстр"), 
	  OOZE("тина"),
	  OUTSIDER("потустаронний"),
	  PLANT("растение"),
	  DEVIL("демон", "изверг"),
	  UNDEAD("нежить"),
	  VERMIN("паразит"), 
	  SLIME("слизь"),
	  SMALL_BEAST("стая крошечных зверей","крохотных зверей", "рой крошечных зверей");

	private final String displayCyrilicName;
	private Set<String> cyrilicNames;

	private CreatureType(String... cyrilicNames) {
		this.displayCyrilicName = cyrilicNames[0];
		this.cyrilicNames = new HashSet<>(Arrays.asList(cyrilicNames));
	}

	public String getCyrilicName() {
		return this.displayCyrilicName;
	}

	public static CreatureType parse(String type) {
		return Arrays.stream(values())
				.filter(t-> t.cyrilicNames.contains(type))
				.findFirst()
				.orElseThrow(()-> new IllegalArgumentException(type));
	}
}
