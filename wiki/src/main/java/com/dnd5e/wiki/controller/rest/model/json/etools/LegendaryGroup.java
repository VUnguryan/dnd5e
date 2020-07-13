package com.dnd5e.wiki.controller.rest.model.json.etools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "source" })
public class LegendaryGroup {
	@JsonProperty("name")
	private String name;
	@JsonProperty("source")
	private String source;
}