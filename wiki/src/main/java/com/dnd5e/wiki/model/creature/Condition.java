package com.dnd5e.wiki.model.creature;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Состояния существ
 * 
 */
@Getter
public enum Condition {
	BLINDED(8, "ослепление", "слепота"), // 0
	CHARMED(11, "очарование", "очарован"), // 1
	DYING(-1,"смерть"), //2 
	EVASIONS(-2, "уклонение"), //3
	DEAFENED(5, "глухота", "оглохший"),  //4
	EXHAUSTION(10, "истощение"), //5 exhaustion
	FRIGHTENED(2, "испуг", "страх", "испуган"), //6
	GRAPPLED(15, "захват", "схваченный"), // 7
	INCAPACITATED(4, "недееспособность"), //8
	INVISIBLE(3, "невидимый"), //9
	PARALYZED(13, "паралич", "парализован"), // 10
	PETRIFIED(6, "окаменение", "отравлен"), // "окаменен" //11
	POISONED(9, "отравление", "отравлен"), //12
	PRONE(14, "сбивание с ног", "Сбитый с ног / Лежащий ничком"), //13
	RESTRAINED(7, "опутанность"), //14
	STUNNED(12, "ошеломление"), //15
	UNCONSCIOUS(1, "бессознательность"); //16

	private String cyrilicName;
	private Set<String> names;
	private Integer id;
	
	Condition(int id, String ...  names){
		this.id = id;
		cyrilicName = names[0];
		this.names = Arrays.stream(names).collect(Collectors.toSet());
	}

	public static Condition parse(String stateString) {
		return Arrays.stream(values())
				.filter(s -> s.getNames().stream().anyMatch(stateString::equalsIgnoreCase))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(stateString));
	}
	
	public static Set<Condition> getImmunity() {
		return EnumSet.of(BLINDED, CHARMED, DEAFENED,EXHAUSTION,FRIGHTENED, GRAPPLED, PARALYZED, PETRIFIED, POISONED, PRONE, RESTRAINED, STUNNED, UNCONSCIOUS);
	}
}