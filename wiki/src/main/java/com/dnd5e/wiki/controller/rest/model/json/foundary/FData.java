package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FData {
	private FAbilities abilities;
	private FAttributes attributes;
	private FDetails details;
	private FTraits traits;
	private FSkills skills;
	
	public FData(Creature creature) {
		abilities = new FAbilities(creature);
		attributes = new FAttributes(creature);
		details = new FDetails(creature);
		traits = new FTraits(creature);
		skills = new  FSkills(creature.getSkills());
	}	
}