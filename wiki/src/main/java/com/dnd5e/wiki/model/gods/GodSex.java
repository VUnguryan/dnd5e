package com.dnd5e.wiki.model.gods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GodSex {
	MALE("бог"),
	FEMALE("богиня"),
	UNDEFINE("божество");
	
	private String name;
}
