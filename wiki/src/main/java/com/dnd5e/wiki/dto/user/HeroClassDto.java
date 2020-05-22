package com.dnd5e.wiki.dto.user;

import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeroClassDto {
	private Integer id;
	private String name;
	public HeroClassDto(HeroClass heroClass) {
		id = heroClass.getId();
		name = heroClass.getName();
	}
}
