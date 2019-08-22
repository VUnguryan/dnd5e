package com.dnd5e.wiki.model.gods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GodSex {
	MALE("бог"),
	FEMALE("богиня"),
	PHILOSOPHY("философия"),
	UNDEFINE("божество");
	
	private String name;
	
	public static GodSex parse(String sex) {
		for (GodSex godSex : values()) {
			if (godSex.name.equals(sex)) {
				return godSex;
			}
		}
		return UNDEFINE;
	}
}
