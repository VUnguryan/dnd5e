package com.dnd5e.wiki.model.creature;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Состояния
 * 
 */
@Getter
public enum State {
	BLINDED("ослепление", "слепота"),
	CHARMED("очарование", "очарован"), // 1
	DYING("смерть"), //2 
	DODGING("уклонение"), //3
	DEAFENED("глухота"),  //4
	EXHAUSTING("истощение"), //5 
	FRIGHTENED("испуг", "страх", "испуган"), //6
	GRAPPLED("захват"), // 7
	INCAPACITATED("недееспособность"), //8
	INVISIBLE("невидимый"), //9
	PARALYZED("паралич", "парализован"), // 10
	PETRIFIED("окаменение", "отравлен"), // "окаменен" //11
	POISONED("отравление", "отравлен"), //12
	PRONE("сбивание с ног", "Сбитый с ног / Лежащий ничком"), //13
	RESTRAINED("опутанность"), //14
	STUNNED("ошеломление"), //15
	UNCONSCIOUS("бессознательность"); //16

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
				.orElseThrow(() -> new IllegalArgumentException(stateString));
	}
	public static Set<State> getImmunity() {
		return EnumSet.of(BLINDED, CHARMED, DEAFENED,EXHAUSTING,FRIGHTENED, GRAPPLED, PARALYZED, PETRIFIED, POISONED, PRONE, RESTRAINED, STUNNED, UNCONSCIOUS);
	}
}