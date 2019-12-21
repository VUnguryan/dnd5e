package com.dnd5e.wiki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sense {
	DARKVISION("темное зрение"),
	BLINDSIGHT("слепое зрение"),
	TREMORSENSE("чувство вибрации"),
	TRUESIGHT("истинное зрение");
	private String cyrilicName;
}