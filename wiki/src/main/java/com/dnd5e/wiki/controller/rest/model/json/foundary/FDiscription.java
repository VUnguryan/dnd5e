package com.dnd5e.wiki.controller.rest.model.json.foundary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FDiscription {
	private String value;
	private String chat ="";
	private String unidentified ="";

	public FDiscription(String description) {
		value = description;
	}
}
