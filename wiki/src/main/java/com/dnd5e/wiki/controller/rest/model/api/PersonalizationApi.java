package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.hero.Personalization;

import lombok.Getter;

@Getter
public class PersonalizationApi {
	private String type;
	private String text;
	public PersonalizationApi(Personalization personalization) {
		type = personalization.getType().getName();
		text = personalization.getText();
	}
}