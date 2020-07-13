package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.dnd5e.wiki.controller.rest.model.xml.Compendium;
import com.dnd5e.wiki.model.creature.CreatureTrait;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "entries" })
public class Trait {

	@JsonProperty("name")
	private String name;
	@JsonProperty("entries")
	private List<String> entries = null;
	
	public Trait(CreatureTrait trait) {
		name = trait.getName();
		entries = Arrays.stream(trait.getDescription().split("<p>"))
				.filter(Objects::nonNull)
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(Compendium::removeHtml)
				.map(String::trim)
				.collect(Collectors.toList());
	}
}