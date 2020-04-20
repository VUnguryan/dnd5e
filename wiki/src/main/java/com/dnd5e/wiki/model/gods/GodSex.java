package com.dnd5e.wiki.model.gods;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GodSex {
	MALE("бог"),
	FEMALE("богиня"),
	MAIN("главное божество"),
	JUNIOR("младщий бог"),
	HALF("полу-бог"),
	PHILOSOPHY("философия"),
	UNDEFINE("божество");
	
	private String cyrilicName;
	
	public static GodSex parse(String sex) {
		for (GodSex godSex : values()) {
			if (godSex.cyrilicName.equals(sex)) {
				return godSex;
			}
		}
		return UNDEFINE;
	}
}