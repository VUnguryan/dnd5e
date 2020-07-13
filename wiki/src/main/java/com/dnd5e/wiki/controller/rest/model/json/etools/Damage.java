package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Damage {
	@JsonProperty("resist")
	private List<String> resist = Collections.emptyList();
	@JsonProperty("note")
	private String note;
	public Damage(String note) {
		this.note = note;
	}
}