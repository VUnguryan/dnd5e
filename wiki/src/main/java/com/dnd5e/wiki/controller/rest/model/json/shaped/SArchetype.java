package com.dnd5e.wiki.controller.rest.model.json.shaped;

import java.util.List;

import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SArchetype {
	private String name;
	private List<String> spells;
	public SArchetype(com.dnd5e.wiki.model.hero.classes.Archetype archetype) {
		name = StringUtils.capitalize(archetype.getName().toLowerCase());
	}
}