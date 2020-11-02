package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.gods.God;

import lombok.Getter;

@Getter
public class GodApi {
	private int id;
	private String name;
	public GodApi(God god) {
		this.id = god.getId();
		this.name = god.getName();
	}
}