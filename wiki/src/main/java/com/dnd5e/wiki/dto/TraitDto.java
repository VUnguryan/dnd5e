package com.dnd5e.wiki.dto;

import java.util.stream.Collectors;

import org.thymeleaf.util.StringUtils;

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
	private String englishName;
	private String requirement;
	private String abilities;
	private String skills;
	private String description;
	private String book;
	
	public TraitDto(Trait trait) {
		name = StringUtils.capitalizeWords(trait.getName().toLowerCase())
				.replace(" И ", " и ").replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ").replace(" От ", " от ").replace(" По ", " по ")
				.replace(" Над ", " над ").replace(" В ", " в ");
		englishName = trait.getEnglishName();
		requirement = trait.getRequirement();
		abilities = trait.getAbilities().stream().map(AbilityType::getCyrilicName).collect(Collectors.joining(", "));
		skills = trait.getSkills().stream().map(SkillType::getCyrilicName).collect(Collectors.joining(", "));
		description = trait.getDescription();
		book = trait.getBook().getName();
	}
}