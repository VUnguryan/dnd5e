package com.dnd5e.wiki.model.creature;

/**
 * Состояния
 * 
 * @author VUnguryan
 *
 */
public enum State {
	BLINDED("Ослеплённый"),
	CHARMED("Очарованный"),
	DYING("Умирающий"),
	DODGING("Уклонение"),
	DEAFENED("Оглохший"),
	EXHAUSTING("Истощенный"),
	FRIGHTENED("Испуганный"),
	GRAPPLED("Схваченный"),
	INCAPACITATED("Недееспособный"),
	INVISIBLE("Невидимый"),
	PARALYZED("Парализованный"),
	PETRIFIED("Окаменевший"),
	POISONED("Отравленный"),
	PRONE("Сбитый с ног / Лежащий"),
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