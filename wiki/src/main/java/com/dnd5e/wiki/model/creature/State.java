package com.dnd5e.wiki.model.creature;

/**
 * Состояния
 * 
 * @author VUnguryan
 *
 */
public enum State {
	BLINDED("ослепление"),
	CHARMED("очарование"),
	DYING("смерть"),
	DODGING("уклонение"),
	DEAFENED("глухота"),
	EXHAUSTING("истощение"),
	FRIGHTENED("испуг"),
	GRAPPLED("захват"),
	INCAPACITATED("недееспособность"),
	INVISIBLE("невидимый"),
	PARALYZED("паралич"),
	PETRIFIED("окаменение"), // "окаменен"
	POISONED("отравление"),
	PRONE("сбивание с ног"),
	RESTRAINED("опутанность"),
	STUNNED("ошеломление"),
	UNCONSCIOUS("бессознательность");
	
	private String cyrilicName;

	State(String cyrilicName) {
		this.cyrilicName = cyrilicName;
	}

	public String getCyrilicName() {
		return cyrilicName;
	}

	public static State parse(String stateString) {
		for (State state : values()) {
			if (state.cyrilicName.equals(stateString)) {
				return state;
			}
		}
		return null;
	}
}