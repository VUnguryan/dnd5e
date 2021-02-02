package com.dnd5e.wiki.model.spell;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeUnit {
	BONUS("бонусное действие"), 
	REACTION("реакция"),
	ACTION("действие"), 
	
	MINUTE("минута"),
	HOUR("час");

	private String name;
}