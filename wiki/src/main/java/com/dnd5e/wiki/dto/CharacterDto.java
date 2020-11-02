package com.dnd5e.wiki.dto;

import com.dnd5e.wiki.model.tavern.UserCharacter;

import lombok.Getter;

@Getter
public class CharacterDto {
	private int id;
	private int exp;
	private String name;
	private String race;
	private String className;
	
	public CharacterDto(UserCharacter userCharacter) {
		this.id = userCharacter.getId();
		this.name = userCharacter.getName();
		this.exp = userCharacter.getLevel();
		this.race = userCharacter.getRace().getName();
		this.className = userCharacter.getHeroClass().getName();
	}
}
