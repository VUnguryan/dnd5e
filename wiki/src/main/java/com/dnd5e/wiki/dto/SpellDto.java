package com.dnd5e.wiki.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.dto.user.HeroClassDto;
import com.dnd5e.wiki.model.creature.DamageType;
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
	private String upperLevel;
	private String book;
	private String damageType;
	private List<HeroClassDto> heroClass;
	private String englishName;
	
	public SpellDto(Spell spell) {
		level = spell.getLevel() == 0 ? "Заговор" : String.valueOf(spell.getLevel());
		name = spell.getName();
		ritual = String.valueOf(spell.getRitual());
		concentration = String.valueOf(spell.getConcentration());
		school = spell.getSchool().getName();
		timeCast = spell.getTimeCast();
		distance = spell.getDistance();
		duration = spell.getDuration();
		verbalComponent = spell.getVerbalComponent() !=null && spell.getVerbalComponent() ? "★" : "";
		somaticComponent = spell.getSomaticComponent() != null  && spell.getSomaticComponent() ? "★": "";
		materialComponent = spell.getMaterialComponent() != null && spell.getMaterialComponent() ? "★": "";
		components = spell.getAdditionalMaterialComponent();
		description = spell.getDescription();
		upperLevel = spell.getUpperLevel();
		book = spell.getBook().getName();
		damageType = spell.getDamageType().isEmpty() ? "" : spell.getDamageType().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		heroClass = spell.getHeroClass().stream().map(HeroClassDto::new).collect(Collectors.toList());
		englishName = spell.getEnglishName();
	}
}