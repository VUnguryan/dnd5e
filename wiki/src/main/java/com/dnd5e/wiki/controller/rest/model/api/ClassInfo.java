package com.dnd5e.wiki.controller.rest.model.api;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Getter;

@Getter
public class ClassInfo {
	private String name;
	private byte diceHp;
	private List<String> availableSkills; 
	public ClassInfo(HeroClass heroClass) {
		name = heroClass.getName();
		diceHp = heroClass.getDiceHp();
		availableSkills = heroClass.getAvailableSkills().stream().map(SkillType::getCyrilicName).collect(Collectors.toList());
	}
}