package com.dnd5e.wiki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeBook {
	OFFICAL("официальное"),
	MODULE("приключение"),
	CUSTOM("хомрул");
	private String name;
}