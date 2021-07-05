package com.dnd5e.wiki.dto;

import com.dnd5e.wiki.model.hero.classes.Archetype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchitypeDto {
	private int classId;
	private String className;
	private String classEnglishName;
	private int architypeId;
	private String architypeName;
	private String architypeEnglishName;
	public ArchitypeDto(Archetype archetype) {
		classId = archetype.getHeroClass().getId();
		architypeId = archetype.getId();
		classEnglishName = archetype.getHeroClass().getEnglishName();
		className = archetype.getHeroClass().getName();
		architypeName = archetype.getName().toLowerCase();
		architypeEnglishName = archetype.getEnglishName();
	}
}