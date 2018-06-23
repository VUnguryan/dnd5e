package com.dnd5e.wiki.model.creature;

/**
 * Состояния
 * 
 * @author VUnguryan
 *
 */
public enum State {
	BLINDED("Ослеплённый"),
	CHARMED("очарование"),
	DYING("Умирающий"),
	DODGING("Уклонение"),
	DEAFENED("Оглохший"),
	EXHAUSTING("истощение"),
	FRIGHTENED("Испуганный"),
	GRAPPLED("Схваченный"),
	INCAPACITATED("Недееспособный"),
	INVISIBLE("Невидимый"),
	PARALYZED("паралич"),
	PETRIFIED("окаменение"),
	POISONED("отравление"),
	PRONE("сбивание с ног"),
	RESTRAINED("Опутанный"),
	STUNNED("Ошеломлённый"),
	UNCONSCIOUS("Бессознательный");
	
	private String cyrilicName;

	State(String cyrilicName) {
		this.cyrilicName = cyrilicName;
	}

	public String getCyrilicName() {
		return cyrilicName;
	}
}