package com.dnd5e.wiki.dto.user;

import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpellDto {
	private String level;
	private String name;
	private String ritual;
	private String concentration;
	private String school;
	private String verbalComponent;
	private String somaticComponent;
	private String materialComponent;
	private String components;
	private String timeCast;
	private String distance;
	private String duration;
	private String description;
	private String source;
	
	public SpellDto(Spell spell) {
		level = spell.getLevel() == 0 ? "вызов" : String.valueOf(spell.getLevel());
		name = spell.getName();
		ritual = String.valueOf(spell.getRitual());
		concentration = String.valueOf(spell.getConcentration());
		school = spell.getSchool().name();
		timeCast = spell.getTimeCast();
		distance = spell.getDistance();
		duration = spell.getDuration();
		verbalComponent = spell.getVerbalComponent() !=null && spell.getVerbalComponent() ? "+" : "";
		somaticComponent = spell.getSomaticComponent() != null  && spell.getSomaticComponent() ? "+": "";
		materialComponent = spell.getMaterialComponent() != null && spell.getMaterialComponent() ? "+": "";
		components = spell.getAdditionalMaterialComponent();
		description = spell.getDescription();
		source = spell.getBook().getName();
	}
}