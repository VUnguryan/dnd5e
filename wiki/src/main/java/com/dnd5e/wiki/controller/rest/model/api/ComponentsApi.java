package com.dnd5e.wiki.controller.rest.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentsApi {
	private Boolean v;
	private Boolean s;
	private String m;
}