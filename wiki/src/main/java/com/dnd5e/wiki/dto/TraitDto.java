package com.dnd5e.wiki.dto;

import java.util.stream.Collectors;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Trait;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TraitDto {
	private String name;
	private String requirement;
	private String abilities;
	private String skills;
	private String description;
	private String book;
	
	public TraitDto(Trait trait) {
		name = trait.getName();
		requirement = trait.getRequirement();
		abilities = trait.getAbilities().stream().map(AbilityType::getCyrilicName).collect(Collectors.joining(", "));
		skills = trait.getSkills().stream().map(SkillType::getCyrilicName).collect(Collectors.joining(", "));
		description = trait.getDescription();
		book = trait.getBook().getName();
	}
}