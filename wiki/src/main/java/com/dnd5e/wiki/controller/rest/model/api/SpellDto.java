package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;

@Getter
public class SpellDto {
	private int id;
	private String name;
	public SpellDto(Spell spell) {
		id = spell.getId();
		name = spell.getName();
	}
}
