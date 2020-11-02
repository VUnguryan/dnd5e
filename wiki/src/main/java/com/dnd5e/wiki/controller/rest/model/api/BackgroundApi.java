package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.hero.Background;

import lombok.Getter;

@Getter
public class BackgroundApi {
	private int id;
	private String name;
	private String englishName;
	private String source;
	public BackgroundApi(Background background) {
		this.id = background.getId();
		this.name = background.getName();
		this.englishName = background.getEnglishName();
		this.source = background.getBook().getSource();
	}
}