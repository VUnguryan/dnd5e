package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FBiography {
	private String value;
    @JsonProperty("public")
	private String publicText;
}
