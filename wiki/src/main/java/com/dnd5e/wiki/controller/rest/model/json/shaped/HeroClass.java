package com.dnd5e.wiki.controller.rest.model.json.shaped;

import java.util.List;
import java.util.stream.Collectors;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.spell.Spell;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class HeroClass {
	private String name;
	private List<Archetype> archetypes;
	private List<String> spells;
	public HeroClass(com.dnd5e.wiki.model.hero.classes.HeroClass heroClass) {
		name = heroClass.getEnglishName();
		archetypes = heroClass.getArchetypes().stream().map(Archetype::new).collect(Collectors.toList());
		spells = heroClass.getSpells().stream().map(Spell::getName).map(String::toLowerCase).map(StringUtils::capitalize).collect(Collectors.toList());
	}
}