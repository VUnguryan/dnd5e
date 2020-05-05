package com.dnd5e.wiki.model.hero.madness;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MadnessType {
	SHORT("кратковременное","1к10 минут"),
	LONG("долговременное","1к10 × 10 часов"), 
	UNLIMITED("бессрочное", "до исцеления");
	
	private String cyrilicName;
	private String duration;
}