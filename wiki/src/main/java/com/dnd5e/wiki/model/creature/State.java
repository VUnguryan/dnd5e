package com.dnd5e.wiki.model.creature;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Состояния
 * 
 * @author VUnguryan
 *
 */
@Getter
@AllArgsConstructor
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

	public static State parse(String stateString) {
		return Arrays.stream(values())
				.filter(s -> s.getCyrilicName().equals(stateString))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}