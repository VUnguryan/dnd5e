package com.dnd5e.wiki.model.creature;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Состояния
 * 
 * @author VUnguryan
 *
 */
@Getter
public enum State {
	BLINDED("ослепление", "слепота"),
	CHARMED("очарование"),
	DYING("смерть"),
	DODGING("уклонение"),
	DEAFENED("глухота"),
	EXHAUSTING("истощение"),
	FRIGHTENED("испуг", "страх"),
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
	private Set<String> names;
	State(String ...  names){
		cyrilicName = names[0];
		this.names = Arrays.stream(names).collect(Collectors.toSet());
	}
	public static State parse(String stateString) {
		return Arrays.stream(values())
				.filter(s -> s.getNames().stream().anyMatch(stateString::equalsIgnoreCase))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}