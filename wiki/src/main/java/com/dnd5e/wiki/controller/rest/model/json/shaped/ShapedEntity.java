package com.dnd5e.wiki.controller.rest.model.json.shaped;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ShapedEntity {
	private String name = "SRD";
	private String version = "2.0.0";
	
	@JsonProperty("classes")
	private List<SHeroClass> classes; 
	
	@JsonProperty("monsters")
	private List<SMonster> monsters;
	
	@JsonProperty("spells")
	private List<SSpell> spells;
}