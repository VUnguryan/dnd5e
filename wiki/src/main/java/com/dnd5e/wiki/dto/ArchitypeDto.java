package com.dnd5e.wiki.dto;

import com.dnd5e.wiki.model.hero.classes.Archetype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchitypeDto {
	private int classId;
	private String className;
	private int architypeId;
	private String architypeName;
	public ArchitypeDto(Archetype archetype) {
		classId = archetype.getHeroClass().getId();
		architypeId = archetype.getId();
		className = archetype.getHeroClass().getName();
		architypeName = archetype.getName().toLowerCase();
	}
}