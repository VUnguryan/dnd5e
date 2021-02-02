package com.dnd5e.wiki.controller.rest.model.api;

import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Getter;

@Getter
public class ClassApi {
	private int id;
	private String name;
	public ClassApi(HeroClass heroClass) {
		id = heroClass.getId();
		name = heroClass.getName();
	}
}