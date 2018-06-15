package com.dnd5e.wiki.model.creature;

/**
 * Навыки 
 * @author VUnguryan
 *
 */
public enum Skill {
	ATHLETICS("Атлетика"),
	ACROBATICS("Акробатика"),
	SLEIGHT_OF_HAND("Лoвкость рук"),
	STEALTH("Скрытность"),
	ARCANA("Магия"),
	HISTORY("Магия"),
	INVESTIGATION("Анализ"),
	NATURE("Природа"),
	RELIGION("Религия"),
	ANIMAL_HANDLING("Уход за животными"),
	INSIGHT("Проницательность"),
	MEDICINE("Медицина"),
	PERCEPTION("Внимательность"),
	SURVIVAL("Выживание"),
	DECEPTION("Обман"),
	INTIMIDATION("Запугивание"),
	PERFORMANCE("Выступление"),
	PERSUASION("Убеждение");
	
	private String cyrilicName;
	
	Skill(String cyrilicName){
		this.cyrilicName = cyrilicName;
	}
	public String getCyrilicName() {
		return cyrilicName;
	}
}
