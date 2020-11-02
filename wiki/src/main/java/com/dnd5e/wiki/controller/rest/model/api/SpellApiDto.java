package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;

@Getter
public class SpellApiDto {
	private int id;
	private String name;
	public SpellApiDto(Spell spell) {
		id = spell.getId();
		name = spell.getName();
	}
}
