package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FTraits {
	private String size;
	private FDamage di;
	private FDamage dr;
	private FDamage dv;
	private FCondition ci;
	private String senses;
	private FLanguages languages;
	private FSkills skills;

	public FTraits(Creature creature) {
		switch (creature.getSize()) {
		case TINY:
			size = "tiny";
			break;
		case SMALL:
			size = "sm";
			break;
		case MEDIUM:
			size = "med";
			break;
		case LARGE:
			size = "lg";
			break;
		case HUGE:
			size = "huge";
			break;
		case GARGANTUAN:
			size = "grg";
			break;
		default:
			size ="";
			break;
		}
		di = new FDamage(creature, creature::getImmunityDamages);
		dr = new FDamage(creature, creature::getResistanceDamages);
		dv = new FDamage(creature, creature::getVulnerabilityDamages);
		ci = new FCondition(creature);
		senses = creature.getSense();
		languages = new FLanguages(creature.getLanguages());
		skills = new FSkills(creature.getSkills());
	}
}