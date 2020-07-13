package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.dnd5e.wiki.controller.rest.model.xml.Compendium;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "entries" })
public class Action {

	@JsonProperty("name")
	private String name;
	@JsonProperty("entries")
	private List<String> entries = null;
	public Action(com.dnd5e.wiki.model.creature.Action action) {
		name = action.getName();
		entries = Arrays.stream(action.getDescription().split("<p>"))
			.filter(Objects::nonNull)
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.map(Compendium::removeHtml)
			.map(String::trim)
			.collect(Collectors.toList());
	}
}