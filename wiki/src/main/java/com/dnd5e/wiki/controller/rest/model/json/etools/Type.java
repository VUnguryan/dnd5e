package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.Creature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Type {
	@JsonProperty("type")
	private String type;
	@JsonProperty("tags")
	private List<String> tags;
	public Type(Creature creature) {
		type = creature.getType().name().toLowerCase();
		if (creature.getRaceName()!= null) {
			tags = Arrays.stream(creature.getRaceName().split(",")).map(r -> r.replace("(", "")).map(r -> r.replace(")", "")).collect(Collectors.toList());			
		}
	}
}