package com.dnd5e.wiki.builder.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbilityInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Set<Byte> strengthArray;
	private Set<Byte> dexterityArray;
	private Set<Byte> constitutionArray;
	private Set<Byte> intellectArray;
	private Set<Byte> wizdomArray;
	private Set<Byte> charismaArray;
	
	private int points;
	private Map<Integer, String> strengtPoints;
	private Map<Integer, String> dexterityPoints;
	private Map<Integer, String> constitutionPoints;
	private Map<Integer, String> intellectPoints;
	private Map<Integer, String> wizdomPoints;
	private Map<Integer, String> charismaPoints;
	
	private String pointMethod;
	
	private byte strength;
	private byte dexterity;
	private byte constitution;
	private byte intellect;
	private byte wizdom;
	private byte charisma;
}