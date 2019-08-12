package com.dnd5e.wiki.model.spell;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Component {
	VERBAL("вербальный"),
	SOMATIC("соматический"),
	MATERIAL("материальный");

	private String name;
}
