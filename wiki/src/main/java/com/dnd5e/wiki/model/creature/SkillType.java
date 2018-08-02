package com.dnd5e.wiki.model.creature;

/**
 * Навыки 
 * @author VUnguryan
 *
 */
public enum SkillType {
	ATHLETICS("Атлетика"),
	ACROBATICS("Акробатика"),
	SLEIGHT_OF_HAND("Лoвкость рук"),
	STEALTH("Скрытность"),
	ARCANA("Магия"),
	HISTORY("История"),
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
	
	SkillType(String cyrilicName){
		this.cyrilicName = cyrilicName;
	}

	public String getCyrilicName() {
		return cyrilicName;
	}

	public static SkillType parse(String cyrilicName) {
		for (SkillType type : values()) {
			if (type.cyrilicName.equals(cyrilicName)) {
				return type;
			}
		}
		return null;
	}
}
