package com.dnd5e.wiki.model;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public enum TypeBook {
	OFFICAL("официальное"),
	MODULE("приключение"),
	CUSTOM("хомрул");

	private String name;
}