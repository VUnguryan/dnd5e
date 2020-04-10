package com.dnd5e.wiki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeBook {
	OFFICAL("официальное издание"),
	CUSTOM("НЕ официальное издание"),
	MODULE("модуль приключения");
	private String name;
}
