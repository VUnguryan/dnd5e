package com.dnd5e.wiki.builder.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;

	private byte strength;
	private byte dexterity;
	private byte constitution;
	private byte intellect;
	private byte wizdom;
	private byte charisma;

	public void clearBonuses() {
		strength = 0;
		dexterity = 0;
		constitution = 0;
		intellect = 0;
		wizdom = 0;
		charisma = 0;
	}
}