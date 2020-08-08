package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.hero.Personalization;

import lombok.Getter;

@Getter
public class PersonalizationDto {
	private String type;
	private String text;
	public PersonalizationDto(Personalization personalization) {
		type = personalization.getType().getName();
		text = personalization.getText();
	}
}