package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAttributes {
	private FAC ac;
	private FHP hp;
	private FMovement movement;
	private byte prof;
	private byte spelldc = 10;
	public FAttributes(Creature creature) {
		ac = new FAC(creature.getAC(), (byte) 0);
		hp = new FHP(creature);
		movement = new FMovement(creature);
	}
}